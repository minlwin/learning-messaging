package com.jdc.progress.api.output;

import java.time.LocalDate;

import com.jdc.progress.mode.entity.EscInvoice.Status;
import com.jdc.progress.mode.entity.EscInvoicePk;

public interface EscInvoiceInfo {

	EscInvoicePk getId();
	
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
	Status getStatus();
	LocalDate getLastPaidDate();
	LocalDate getBillLastDate();	
}
