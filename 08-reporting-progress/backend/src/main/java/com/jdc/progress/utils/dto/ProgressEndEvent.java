package com.jdc.progress.utils.dto;

import com.jdc.progress.model.entity.EscUploadHistory.UploadState;

public record ProgressEndEvent(String historyId, UploadState state) {

	public String getError() {
		return "Fails in %s.".formatted(state);
	}
}
