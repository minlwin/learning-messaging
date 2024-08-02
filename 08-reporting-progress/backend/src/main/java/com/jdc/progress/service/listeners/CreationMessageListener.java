package com.jdc.progress.service.listeners;

import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.jdc.progress.model.entity.EscUploadHistory.UploadState;
import com.jdc.progress.service.EscInvoiceCreationService;
import com.jdc.progress.service.StateMessageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreationMessageListener {

	private final StateMessageService stateMessageService;
	private final EscInvoiceCreationService service;
	
	@RabbitListener(queues = "#{createQueue.name}")
	public void handle(String message) {

		log.info("DB Insert Start -> {}", message);
		var historyId = UUID.fromString(message);
		
		if(service.create(historyId)) {
			// Next Process
			stateMessageService.send(historyId, UploadState.Success);
		} else {
			// Next Process
			stateMessageService.send(historyId, UploadState.Error);
		}
	}

}
