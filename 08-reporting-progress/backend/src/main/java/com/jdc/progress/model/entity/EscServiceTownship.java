package com.jdc.progress.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ESC_SERVICE_TOWNSHIP")
public class EscServiceTownship {

	@Id
	private String code;
	
	@Column(nullable = false)
	private String township;
	
	@Column(nullable = false)
	private String service;
}
