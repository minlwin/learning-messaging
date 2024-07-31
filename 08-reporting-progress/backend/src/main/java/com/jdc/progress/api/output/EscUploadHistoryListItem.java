package com.jdc.progress.api.output;

import java.time.LocalDateTime;
import java.util.UUID;

import com.jdc.progress.model.entity.EscUploadHistory;
import com.jdc.progress.model.entity.EscUploadHistory.ErrorType;
import com.jdc.progress.model.entity.EscUploadHistory.UploadState;
import com.jdc.progress.model.entity.EscUploadHistory_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record EscUploadHistoryListItem(
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
		int errors		
		) {

	public static void select(CriteriaQuery<EscUploadHistoryListItem> cq, Root<EscUploadHistory> root) {
		cq.multiselect(
			root.get(EscUploadHistory_.id),
			root.get(EscUploadHistory_.system),
			root.get(EscUploadHistory_.fileName),
			root.get(EscUploadHistory_.uploadBy),
			root.get(EscUploadHistory_.state),
			root.get(EscUploadHistory_.uploadAt),
			root.get(EscUploadHistory_.readAt),
			root.get(EscUploadHistory_.savedAt),
			root.get(EscUploadHistory_.validatedAt),
			root.get(EscUploadHistory_.createdAt),
			root.get(EscUploadHistory_.fileName),
			root.get(EscUploadHistory_.errorType),
			root.get(EscUploadHistory_.records),
			root.get(EscUploadHistory_.errors)
		);
	}
}
