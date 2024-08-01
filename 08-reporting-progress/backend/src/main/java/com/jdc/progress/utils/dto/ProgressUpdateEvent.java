package com.jdc.progress.utils.dto;

import com.jdc.progress.model.entity.EscUploadHistory.UploadState;

public record ProgressUpdateEvent(
		String historyId,
		UploadState state,
		Integer done,
		Integer total) {

	public double getPersent() {
		return done.doubleValue() / total.doubleValue();
	}
}
