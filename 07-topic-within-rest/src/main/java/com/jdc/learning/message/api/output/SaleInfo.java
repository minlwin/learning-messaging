package com.jdc.learning.message.api.output;

import java.time.LocalDateTime;

import com.jdc.learning.message.model.entity.Product_;
import com.jdc.learning.message.model.entity.Sale;
import com.jdc.learning.message.model.entity.SaleItem_;
import com.jdc.learning.message.model.entity.Sale_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record SaleInfo(
		int id,
		LocalDateTime saleAt,
		long saleItems,
		int totalAmount) {

	public static void project(CriteriaBuilder cb, CriteriaQuery<SaleInfo> cq, Root<Sale> root) {
		
		var item = root.join(Sale_.saleItem);
		var product = item.join(SaleItem_.product);
		
		cq.multiselect(
			root.get(Sale_.id),
			root.get(Sale_.saleAt),
			cb.count(item.get(SaleItem_.id)),
			cb.sum(cb.prod(product.get(Product_.price), item.get(SaleItem_.quantity)))
		);
		
		cq.groupBy(
			root.get(Sale_.id),
			root.get(Sale_.saleAt));
	}
	
	public static SaleInfo from(Sale entity) {
		return new SaleInfo(
				entity.getId(), 
				entity.getSaleAt(), 
				entity.getSaleItem().size(), 
				entity.getSaleItem().stream()
					.mapToInt(a -> a.getQuantity() * a.getProduct().getPrice())
					.sum());
	}
}
