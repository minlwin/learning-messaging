package com.jdc.domain.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface TrxSeqUtils {

	DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyyMMdd");
	
	static String getCode(String header, LocalDate date, int seq) {
		return "%s%s%04d".formatted(header, date.format(DF), seq);
	}
}
