package com.jdc.progress.api.output;

import java.time.LocalDateTime;
import java.util.UUID;

import com.jdc.progress.mode.entity.EscService;
import com.jdc.progress.mode.entity.EscUploadHistory.UploadState;

public interface EscUploadHistoryListItem {

	UUID getId();
	EscService getService();
	String getUploadBy();
	UploadState getState();
	LocalDateTime getUploadAt();
	LocalDateTime getStartAt();
	LocalDateTime getEndAt();
	int getRecords();
	int getErrors();

}
