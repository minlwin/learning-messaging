package com.jdc.learning.message.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class SaleItemPk implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "sale_id")
	private int saleId;
	@Column(name = "product_id")
	private int productId;
}
