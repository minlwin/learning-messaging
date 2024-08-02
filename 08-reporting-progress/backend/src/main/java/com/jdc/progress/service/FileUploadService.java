package com.jdc.progress.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.progress.api.input.EscUploadForm;
import com.jdc.progress.api.output.EscUploadHistoryDetails;
import com.jdc.progress.api.output.EscUploadResult;
import com.jdc.progress.model.entity.EscUploadHistory;
import com.jdc.progress.model.entity.EscUploadHistory.UploadState;
import com.jdc.progress.model.repo.EscUploadHistoryRepo;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class FileUploadService {
	
	private final FileSaveService storageService;
	private final EscUploadHistoryRepo historyRepo;
	
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
				
		return EscUploadResult.from(history);
	}

	@Transactional(readOnly = true)
	public EscUploadHistoryDetails findById(String id) {
		return historyRepo.findById(UUID.fromString(id))
				.map(EscUploadHistoryDetails::from)
				.orElseThrow();
	}

	
}
