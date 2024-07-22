package com.jdc.progress.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jdc.progress.api.output.ProgressMessage;
import com.jdc.progress.mode.entity.EscUploadHistory.UploadState;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProgressMessageService {

	public void send(UUID id, UploadState state, Integer current, Integer totalFiles) {
		var progress = new ProgressMessage(id, state, (current.doubleValue() / totalFiles) * 100, "");
		log.info("progress", progress);
	}

	public void sendError(UUID id, UploadState state) {
		var progress = new ProgressMessage(id, UploadState.Error, 0, "Error in %s.".formatted(state));
		log.info("progress", progress);
	}

}
