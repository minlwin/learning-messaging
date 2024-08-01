package com.jdc.progress.service;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.jdc.progress.model.entity.EscUploadHistory.UploadState;
import com.jdc.progress.utils.dto.ProgressEndEvent;
import com.jdc.progress.utils.dto.ProgressUpdateEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgressMessageService {
	
	private final ApplicationEventPublisher publisher;

	public void send(UUID id, UploadState state, Integer current, Integer totalFiles) {
		var progress = new ProgressUpdateEvent(id.toString(), state, current, totalFiles);
		publisher.publishEvent(progress);
	}

	public void sendError(UUID id, UploadState state, String message) {
		var progress = new ProgressEndEvent(id.toString(), state, message);
		publisher.publishEvent(progress);
	}

}
