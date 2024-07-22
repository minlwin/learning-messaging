package com.jdc.progress.api.input;

import java.time.LocalDate;

import com.jdc.progress.mode.entity.EscInvoice.Status;

public record EscInvoiceSearch(
		String service,
		String township,
		String customerCode,
		Status status,
		LocalDate lastFrom,
		LocalDate lastTo
		) {

}
