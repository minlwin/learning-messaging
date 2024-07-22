package com.jdc.progress.api.output;

import java.util.UUID;

import com.jdc.progress.mode.entity.EscUploadHistory;

public record EscUploadResult(
		UUID id,
		String message) {

	public static EscUploadResult from(EscUploadHistory history) {
		return new EscUploadResult(history.getId(), "Upload for %s has been started.".formatted(history.getFileName()));
	}

}
