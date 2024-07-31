package com.jdc.progress.model.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class EscInvoicePk implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String service;
	
	@Column(name = "customer_id")
	private String customerId;
	
	@Column(name = "last_date")
	private LocalDate lastDate;

}
