package com.jdc.learning.message.api.output;

import com.jdc.learning.message.model.entity.Product;
import com.jdc.learning.message.model.entity.Product_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record ProductInfo(
		int id,
		String name,
		int price) {

	public static void project(CriteriaQuery<ProductInfo> cq, Root<Product> root) {
		cq.multiselect(
			root.get(Product_.id),
			root.get(Product_.name),
			root.get(Product_.price)
		);
	}

}
