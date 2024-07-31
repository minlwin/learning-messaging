package com.jdc.learning.model.io;

import java.time.LocalDateTime;
import java.util.UUID;

import com.jdc.learning.model.entity.ProgressHistory;

public record InitiationResult(
		UUID id,
		LocalDateTime startAt,
		String title) {

	public static InitiationResult from(ProgressHistory entity) {
		return new InitiationResult(entity.getId(), entity.getStartAt(), entity.getTitle());
	}

}
