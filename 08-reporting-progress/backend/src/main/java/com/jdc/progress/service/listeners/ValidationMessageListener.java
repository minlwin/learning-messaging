package com.jdc.progress.service.listeners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jdc.progress.model.entity.EscUploadError;
import com.jdc.progress.model.entity.EscUploadHistory.ErrorType;
import com.jdc.progress.model.entity.EscUploadHistory.UploadState;
import com.jdc.progress.model.repo.EscUploadErrorRepo;
import com.jdc.progress.model.repo.EscUploadHistoryRepo;
import com.jdc.progress.service.ProgressMessageService;
import com.jdc.progress.service.StateMessageService;
import com.jdc.progress.utils.DeleteDirectoryUtils;
import com.jdc.progress.utils.dto.EscErrorInput;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationMessageListener {

	@Value("${app.esc.storage-path}")
	private String storage;

	private final ProgressMessageService progressMessageService;
	private final StateMessageService stateMessageService;
	private final EscUploadHistoryRepo historyRepo;
	private final EscUploadErrorRepo errorRepo;

	@Transactional
	@RabbitListener(queues = "#{validationQueue.name}")
	public void handle(String message) {

		log.info("Validation Start -> {}", message);

		var historyId = UUID.fromString(message);
		var history = historyRepo.getReferenceById(historyId);

		try {
			
			// Error Message List
			var errors = new ArrayList<EscUploadError>();
			
			// Listed files in directory
			var files = Files.list(Path.of(storage, message)).filter(Files::isRegularFile).toList();
			
			for(var i =0; i < files.size(); i ++) {
				
				try(var reader = Files.newBufferedReader(files.get(i))) {
					String line = null;
					while(null != (line = reader.readLine())) {
						
						var array = line.split("\t");

						var messages = check(array);
						
						if(!messages.isEmpty()) {
							errors.add(new EscErrorInput(history, array, messages).entity());
						}
					}
				}
				// Send Progress Message
				progressMessageService.send(historyId, UploadState.Validate, i + 1, files.size());
			}
			
			if(!errors.isEmpty()) {
				// Create Error
				createInvoiceError(historyId, errors);
				
			} else {
				// Update Upload History
				historyRepo.findById(historyId).ifPresent(entity -> {
					entity.setState(UploadState.Validate);
					entity.setValidatedAt(LocalDateTime.now());
				});

				// Next Process
				stateMessageService.send(historyId, UploadState.Create);
			}
			
		} catch (Exception e) {
			e.printStackTrace();

			// Send Progress Message
			progressMessageService.sendError(historyId, UploadState.Validate, e.getMessage());

			// Next Process
			stateMessageService.send(historyId, UploadState.Error);
		}
		
	}
	
	private List<String> check(String[] array) {
		var messages = new ArrayList<String>();
		
		if(array.length != 18) {
			messages.add("Invalid column size.");
		}
		
		if(!StringUtils.hasLength(array[1])) {
			messages.add("Empty column for Ledger No.");
		}

		if(!StringUtils.hasLength(array[2])) {
			messages.add("Empty column for Customer No.");
		}

		if(!StringUtils.hasLength(array[7])) {
			messages.add("Empty column for Last paid date.");
		}

		return messages;
	}
	
	private void createInvoiceError(UUID historyId, List<EscUploadError> errors) {
		
		// Send Progress Message
		progressMessageService.sendError(historyId, UploadState.Validate, "Invalid errors.");

		// Update Upload History
		historyRepo.findById(historyId).ifPresent(history -> {
			history.setState(UploadState.Error);
			history.setFinishedAt(LocalDateTime.now());
			history.setErrorType(ErrorType.Validation);
			history.setErrors(errors.size());
		});	
		
		// Delete Files
		try {
			DeleteDirectoryUtils.delete(storage, historyId.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Save Error
		errorRepo.saveAll(errors);
	}
	
}
