package com.jdc.learning.message.model.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;

@Data
@Entity
public class SaleItem {

	@EmbeddedId
	private SaleItemPk id;
	
	@MapsId("saleId")
	@ManyToOne
	private Sale sale;
	
	@MapsId("productId")
	@ManyToOne
	private Product product;
	
	private int quantity;
}
