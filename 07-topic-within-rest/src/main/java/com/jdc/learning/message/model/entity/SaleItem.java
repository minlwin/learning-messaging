package com.jdc.learning.message.model.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class SaleItem {

	@EmbeddedId
	private SaleItemPk id = new SaleItemPk();
	
	@MapsId("saleId")
	@ManyToOne
	private Sale sale;
	
	@MapsId("productId")
	@ManyToOne
	private Product product;
	
	private int quantity;

	public SaleItem(Product product, int quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}
}
