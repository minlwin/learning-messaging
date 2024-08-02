package com.jdc.progress.utils.dto;

import com.jdc.progress.model.entity.EscUploadHistory.UploadState;

public record ProgressUpdateEvent(
		String historyId,
		UploadState state,
		Integer done,
		Integer total) {

	public String getPercent() {
		Double percent = done.doubleValue() / total.doubleValue() * 100;
		return "%d%%".formatted(percent.intValue());
	}
}
