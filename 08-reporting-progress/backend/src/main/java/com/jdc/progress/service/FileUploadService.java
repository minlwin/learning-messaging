package com.jdc.progress.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.progress.api.input.EscUploadForm;
import com.jdc.progress.api.output.EscUploadResult;
import com.jdc.progress.model.entity.EscUploadHistory;
import com.jdc.progress.model.entity.EscUploadHistory.UploadState;
import com.jdc.progress.model.repo.EscUploadHistoryRepo;


@Service
public class FileUploadService {
	
	@Autowired
	private FileSaveService storageService;
	
	@Autowired
	private HeadersExchange progressExchange;
	
	@Autowired
	private EscUploadHistoryRepo historyRepo;
	
	@Autowired
	private AmqpAdmin amqpAdmin;

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public EscUploadResult upload(EscUploadForm form) {
		
		// Create Upload History
		var history = new EscUploadHistory();
		history.setUploadAt(LocalDateTime.now());
		history.setSystem(form.system());
		history.setUploadBy(form.uploadBy());
		history.setFileName(form.file().getOriginalFilename());
		history.setState(UploadState.Upload);
		
		history = historyRepo.saveAndFlush(history);
		
		storageService.save(history.getId(), form.file());
		
		// Create Queue and Binding
		createQueue(history.getId());
		
		return EscUploadResult.from(history);
	}

	private void createQueue(UUID id) {
		// Create Queue
		var queue = new Queue(id.toString(), true, false, true);
		amqpAdmin.declareQueue(queue);
		
		// Binding with Exchange
		var binding = BindingBuilder.bind(queue)
				.to(progressExchange).whereAll(Map.of("historyId", id.toString()))
				.match();
		amqpAdmin.declareBinding(binding);
	}

	
}
