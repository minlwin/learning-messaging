package com.jdc.learning.message.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private LocalDateTime saleAt;
	
	@OneToMany(mappedBy = "sale", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<SaleItem> saleItem = new ArrayList<SaleItem>();
	
	public Sale(List<SaleItem> items) {
		super();
		for(var item : items) {
			item.setSale(this);
			this.saleItem.add(item);
		}
		this.saleAt = LocalDateTime.now();
	}
}
