package com.jdc.learning.message.api.input;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.learning.message.model.entity.Product;
import com.jdc.learning.message.model.entity.Product_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record ProductSearch(
		String name,
		Integer priceFrom,
		Integer priceTo) {

	public Predicate[] where(CriteriaBuilder cb, Root<Product> root) {
		var list = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(name)) {
			list.add(cb.like(cb.lower(root.get(Product_.name)), name.toLowerCase().concat("%")));
		}
		
		if(null != priceFrom) {
			list.add(cb.ge(root.get(Product_.price), priceFrom));
		}
		
		if(null != priceTo) {
			list.add(cb.le(root.get(Product_.price), priceTo));
		}
		
		return list.toArray(size -> new Predicate[size]);
	}
}
