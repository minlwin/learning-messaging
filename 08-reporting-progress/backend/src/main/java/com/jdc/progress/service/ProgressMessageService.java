package com.jdc.progress.service;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.jdc.progress.model.entity.EscUploadHistory.UploadState;
import com.jdc.progress.utils.dto.ProgressEndEvent;
import com.jdc.progress.utils.dto.ProgressUpdateEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProgressMessageService {
	
	private final ApplicationEventPublisher publisher;

	public void send(UUID id, UploadState state, Integer current, Integer totalFiles) {
		var progress = new ProgressUpdateEvent(id.toString(), state, current, totalFiles);
		log.info("progress", progress);
		publisher.publishEvent(progress);
	}

	public void sendError(UUID id, UploadState state) {
		var progress = new ProgressEndEvent(null, state);
		log.info("progress", progress);
		publisher.publishEvent(progress);
	}

}
