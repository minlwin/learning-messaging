package com.jdc.learning.message.api.input;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.learning.message.model.entity.Inventory;
import com.jdc.learning.message.model.entity.Inventory_;
import com.jdc.learning.message.model.entity.Product_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record InventorySearch(
		String name,
		Integer countFrom,
		Integer countTo
) {

	public Predicate[] where(CriteriaBuilder cb, Root<Inventory> root) {
		var list = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(name)) {
			list.add(cb.like(cb.lower(root.get(Inventory_.product).get(Product_.name)), 
					name.toLowerCase().concat("%")));
		}
		
		if(null != countFrom) {
			list.add(cb.ge(root.get(Inventory_.saleCount), countFrom));
		}
		
		if(null != countTo) {
			list.add(cb.le(root.get(Inventory_.saleCount), countTo));
		}
		
		return list.toArray(size -> new Predicate[size]);
	}
}
