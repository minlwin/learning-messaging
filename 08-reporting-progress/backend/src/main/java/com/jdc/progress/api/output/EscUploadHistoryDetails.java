package com.jdc.progress.api.output;

import java.time.LocalDateTime;
import java.util.UUID;

import com.jdc.progress.model.entity.EscUploadHistory;
import com.jdc.progress.model.entity.EscUploadHistory.ErrorType;
import com.jdc.progress.model.entity.EscUploadHistory.UploadState;

public record EscUploadHistoryDetails(
		UUID id,
		String system,
		String fileName,
		String uploadBy,
		UploadState state,
		LocalDateTime uploadAt,
		LocalDateTime readAt,
		LocalDateTime savedAt,
		LocalDateTime validatedAt,
		LocalDateTime createdAt,
		LocalDateTime finishedAt,
		ErrorType errorType,
		int records,
		int errors) {
	
	public static EscUploadHistoryDetails from(EscUploadHistory entity) {
		return new EscUploadHistoryDetails(entity.getId(), 
				entity.getSystem(), 
				entity.getFileName(), 
				entity.getUploadBy(), 
				entity.getState(), 
				entity.getUploadAt(), 
				entity.getReadAt(), 
				entity.getSavedAt(), 
				entity.getValidatedAt(), 
				entity.getCreatedAt(), 
				entity.getFinishedAt(), 
				entity.getErrorType(), 
				entity.getRecords(), 
				entity.getErrors());
	}

}
