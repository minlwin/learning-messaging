package com.jdc.progress.mode.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ESC_INVOICE")
public class EscInvoice {

	@EmbeddedId
	private EscInvoicePk id;
	
	private int seqNumber;
	@Column(nullable = false)
	private String ledgerNo;
	
	@Column(nullable = false)
	private String customerName;
	
	@Column(nullable = false)
	private String meterNo;
	
	@Column(nullable = false)
	private String address;
	
	private String billCode;
	
	private int usageUnit;
	private int usageFees;
	private int serviceCharges;
	private int horsePower;
	private int discount;
	private int lastBalance;
	private int total;
	private int remainAmount;
	private int connectionFees;
	private int allTotal;
	private Status status;

	private LocalDate mtbDueDate;
	
	@ManyToOne
	private EscUploadHistory history;
	
	public enum Status {
		New, Paid, Timeout
	}
}
