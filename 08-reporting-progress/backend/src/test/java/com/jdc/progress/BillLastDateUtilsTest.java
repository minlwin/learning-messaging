package com.jdc.progress;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.jdc.progress.utils.BillLastDateUtils;

public class BillLastDateUtilsTest {

	@Test
	void test() {
		
		var start = LocalDate.of(2024, 7, 1);
		var end = start.plusDays(7);
		
		while(start.isBefore(end)) {
			
			var lastDate = BillLastDateUtils.calculate(start);
			
			System.out.printf("Date : %s%n", start);
			System.out.printf("DAY  : %s%n", start.getDayOfWeek());
			System.out.printf("Last : %s%n", lastDate);
			System.out.printf("DAY : %s%n%n", lastDate.getDayOfWeek());
			
			start = start.plusDays(1);
		}
	}
}
