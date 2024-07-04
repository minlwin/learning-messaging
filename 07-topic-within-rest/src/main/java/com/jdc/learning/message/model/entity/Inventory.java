package com.jdc.learning.message.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Inventory {

	@Id
	private int id;
	
	@MapsId
	@OneToOne
	private Product product;

	private int saleCount;
	private int totalSale;
}
