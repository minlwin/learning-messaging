package com.jdc.progress.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ESC_INVOICE", indexes = {
		@Index(columnList = "township")
})
public class EscInvoice {

	@EmbeddedId
	private EscInvoicePk id;
	
	private String township;
	
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
	
	private Integer usageUnit;
	private Integer usageFees;
	private Integer serviceCharges;
	private Integer horsePower;
	private Integer discount;
	private Integer lastBalance;
	private Integer total;
	private Integer remainAmount;
	private Integer connectionFees;
	private Integer allTotal;
	private Status status;

	private LocalDate mtbDueDate;
	
	@ManyToOne
	private EscUploadHistory history;
	
	public enum Status {
		New, Paid, Timeout
	}
}
