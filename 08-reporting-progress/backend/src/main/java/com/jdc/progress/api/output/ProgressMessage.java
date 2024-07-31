package com.jdc.progress.api.output;

import java.util.UUID;

import com.jdc.progress.model.entity.EscUploadHistory.UploadState;

public record ProgressMessage(
		UUID historyId,
		UploadState state,
		double percent,
		String error
		) {

}
