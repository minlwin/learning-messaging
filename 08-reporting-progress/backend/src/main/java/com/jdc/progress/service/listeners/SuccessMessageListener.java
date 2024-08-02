package com.jdc.progress.service.listeners;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.progress.model.entity.EscUploadHistory.UploadState;
import com.jdc.progress.model.repo.EscUploadHistoryRepo;
import com.jdc.progress.utils.DeleteDirectoryUtils;
import com.jdc.progress.utils.dto.ProgressEndEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SuccessMessageListener {

	@Value("${app.esc.storage-path}")
	private String storage;

	private final EscUploadHistoryRepo historyRepo;
	private final ApplicationEventPublisher publisher;

	@Transactional
	@RabbitListener(queues = "#{successQueue.name}")
	public void handle(String message) {
		
		try {
			log.info("Success -> {}", message);

			DeleteDirectoryUtils.delete(storage, message);
			
			historyRepo.findById(UUID.fromString(message)).ifPresent(history -> {
				history.setState(UploadState.Success);
				history.setFinishedAt(LocalDateTime.now());
			});

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			publisher.publishEvent(new ProgressEndEvent(message, UploadState.Success, "Finished Successfully"));
		}
	}

}
