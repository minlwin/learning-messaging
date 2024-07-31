package com.jdc.progress.utils;

import java.time.LocalDate;

public interface BillLastDateUtils {

	public static LocalDate calculate(LocalDate date) {
		return switch (date.getDayOfWeek()) {
		case SUNDAY -> date.minusDays(3);
		case MONDAY -> date.minusDays(4);
		default -> date.minusDays(2);
		};
	}
}
