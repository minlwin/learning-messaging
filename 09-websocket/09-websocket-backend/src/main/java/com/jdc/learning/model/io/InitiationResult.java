package com.jdc.learning.model.io;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jdc.learning.model.entity.ProgressHistory;

public record InitiationResult(
		UUID id,
		@JsonFormat(pattern = "yyyyMMdd HH:mm:ss")
		LocalDateTime startAt,
		String title) {

	public static InitiationResult from(ProgressHistory entity) {
		return new InitiationResult(entity.getId(), entity.getStartAt(), entity.getTitle());
	}

}
