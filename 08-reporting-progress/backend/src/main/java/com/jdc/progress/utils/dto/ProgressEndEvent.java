package com.jdc.progress.utils.dto;

import com.jdc.progress.model.entity.EscUploadHistory.UploadState;

public record ProgressEndEvent(String historyId, UploadState state, String message) {
}
