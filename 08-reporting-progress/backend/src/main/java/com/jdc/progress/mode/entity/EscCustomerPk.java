package com.jdc.progress.mode.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class EscCustomerPk implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String service;

	@Column(name = "customer_id")
	private String customerId;
	
}
