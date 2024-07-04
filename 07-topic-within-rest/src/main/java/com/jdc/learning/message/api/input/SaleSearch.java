package com.jdc.learning.message.api.input;

import java.time.LocalDate;
import java.util.ArrayList;

import com.jdc.learning.message.model.entity.Sale;
import com.jdc.learning.message.model.entity.Sale_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record SaleSearch(
		LocalDate dateFrom,
		LocalDate dateTo) {


	public Predicate[] where(CriteriaBuilder cb, Root<Sale> root) {
		var list = new ArrayList<Predicate>();
		
		if(null != dateFrom) {
			list.add(cb.greaterThanOrEqualTo(root.get(Sale_.saleAt), dateFrom.atStartOfDay()));
		}
		
		if(null != dateTo) {
			list.add(cb.lessThanOrEqualTo(root.get(Sale_.saleAt), dateTo.plusDays(1).atStartOfDay()));
		}
		
		return list.toArray(size -> new Predicate[size]);
	}
}
