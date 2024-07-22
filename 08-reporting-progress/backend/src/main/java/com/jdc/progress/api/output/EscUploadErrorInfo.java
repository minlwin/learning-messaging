package com.jdc.progress.api.output;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.jdc.progress.mode.entity.EscService;

public interface EscUploadErrorInfo {
	
	UUID getId();
	
	EscService getHistoryService();
	
	String getHistoryUploadBy();
	LocalDateTime getHistoryUploadAt();

	String getCustomerName();
	String getCustomerLedgerName();
	String getCustomerMeterNo();
	String getCustomerAddress();
	int getUsageUnit();
	int getUsageFees();
	int getServiceCharges();
	int getHorsePower();
	int getDiscount();
	int getLastBalance();
	int getTotal();
	int getRemainAmount();
	int getConnectionFees();
	int getAllTotal();
	LocalDate getLastPaidDate();
	String getErrors();
}
