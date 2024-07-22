package com.jdc.progress.api.input;

import java.time.LocalDate;

import com.jdc.progress.mode.entity.EscUploadHistory.UploadState;

public record EscUploadHistorySearch(
		String service,
		String township,
		UploadState state,
		LocalDate from,
		LocalDate to) {

}
