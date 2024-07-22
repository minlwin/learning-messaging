package com.jdc.progress.mode.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ESC_CUSTOMER")
public class EscCustomer {

	@EmbeddedId
	private EscCustomerPk id;
	
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String ledgerNo;
	@Column(nullable = false)
	private String meterNo;
	@Column(nullable = false)
	private String address;
}
