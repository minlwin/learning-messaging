package com.jdc.learning.message.api.output;

import com.jdc.learning.message.model.entity.Inventory;
import com.jdc.learning.message.model.entity.Inventory_;
import com.jdc.learning.message.model.entity.Product_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record InventoryInfo(
		int id,
		String product,
		int price,
		int saleCount,
		int saleAmount) {

	public static void project(CriteriaQuery<InventoryInfo> cq, Root<Inventory> root) {
		cq.multiselect(
			root.get(Inventory_.id),
			root.get(Inventory_.product).get(Product_.name),
			root.get(Inventory_.product).get(Product_.price),
			root.get(Inventory_.saleCount),
			root.get(Inventory_.totalSale)
		);
	}

}
