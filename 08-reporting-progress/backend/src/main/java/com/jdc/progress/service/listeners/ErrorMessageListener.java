package com.jdc.progress.service.listeners;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.progress.mode.entity.EscUploadHistory.ErrorType;
import com.jdc.progress.mode.entity.EscUploadHistory.UploadState;
import com.jdc.progress.mode.repo.EscUploadHistoryRepo;
import com.jdc.progress.utils.DeleteDirectoryUtils;

@Service
public class ErrorMessageListener {

	@Value("${app.esc.storage-path}")
	private String storage;
	@Autowired
	private EscUploadHistoryRepo historyRepo;

	@RabbitListener(queues = "#{errorQueue.name}")
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void handle(String message) {
		
		try {
			historyRepo.findById(UUID.fromString(message)).ifPresent(history -> {
				history.setState(UploadState.Error);
				history.setErrorType(ErrorType.System);
				history.setFinishedAt(LocalDateTime.now());
			});
			
			DeleteDirectoryUtils.delete(storage, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
