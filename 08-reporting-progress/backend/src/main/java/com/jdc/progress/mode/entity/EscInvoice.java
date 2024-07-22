package com.jdc.progress.mode.entity;

import java.time.LocalDate;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ESC_INVOICE")
public class EscInvoice {

	@EmbeddedId
	private EscInvoicePk id;
	
	@ManyToOne
	@JoinColumn(name = "service", insertable = false, updatable = false)
	@JoinColumn(name = "customer_id", insertable = false, updatable = false)
	private EscCustomer customer;
	
	@ManyToOne
	private EscUploadHistory history;

	private int seqNumber;
	
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

	private LocalDate billLastDate;
	
	public enum Status {
		New, Paid, Timeout
	}
}
