package com.jdc.progress.utils.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.jdc.progress.mode.entity.EscUploadError;
import com.jdc.progress.mode.entity.EscUploadHistory;

public record EscErrorInput(
		EscUploadHistory history,
		String [] data,
		List<String> errors) {
	
	public EscUploadError entity() {
		var entity = new EscUploadError();
		entity.setId("%s-%06d".formatted(history.getId().toString()));
		entity.setHistory(history);
		entity.setSeqNumber(safe(0, data));
		entity.setLedgerNo(safe(1, data));
		entity.setCustomerId(safe(2, data));
		entity.setMeterNo(safe(3, data));
		entity.setCustomerName(safe(4, data));
		entity.setAddress(safe(5, data));
		entity.setBillCode(safe(6, data));
		entity.setLastDate(safe(7, data));
		entity.setUsageUnit(safe(8, data));
		entity.setUsageFees(safe(9, data));
		entity.setServiceCharges(safe(10, data));
		entity.setHorsePower(safe(11, data));
		entity.setDiscount(safe(12, data));
		entity.setLastBalance(safe(13, data));
		entity.setTotal(safe(14, data));
		entity.setRemainAmount(safe(15, data));
		entity.setConnectionFees(safe(16, data));
		entity.setAllTotal(safe(17, data));
		entity.setError(errors.stream().collect(Collectors.joining(",")));
		return entity;
	}
	
	private static String safe(int index, String [] array) {
		return array.length > index ? array[index] : null;
	}
}
