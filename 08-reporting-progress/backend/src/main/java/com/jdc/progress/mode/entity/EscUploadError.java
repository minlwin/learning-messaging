package com.jdc.progress.mode.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ESC_UPLOAD_ERROR")
public class EscUploadError {
	
	@Id
	private String id;
	
	@ManyToOne
	private EscUploadHistory history;
		
	private String seqNumber;
	private String ledgerNo;
	private String meterNo;
	private String customerId;
	private String customerName;
	private String address;
	private String billCode;
	private String lastDate;
	
	private String usageUnit;
	private String usageFees;
	private String serviceCharges;
	private String horsePower;
	private String discount;
	private String lastBalance;
	private String total;
	private String remainAmount;
	private String connectionFees;
	private String allTotal;
	
	private String error;
}
