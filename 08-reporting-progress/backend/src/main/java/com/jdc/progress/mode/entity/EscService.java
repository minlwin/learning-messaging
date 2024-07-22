package com.jdc.progress.mode.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ESC_SERVICE")
public class EscService {

	@Id
	private String code;
	
	@Column(nullable = false)
	private String township;
	
	@Column(nullable = false)
	private String service;
}
