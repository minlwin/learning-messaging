package com.jdc.agent.api.input;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.domain.entity.AccountForAgent;
import com.jdc.domain.entity.AccountForAgent_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record AgentAccountSearch(
		LocalDate createdFrom,
		LocalDate createdTo,
		String keyword) {

	public Predicate[] where(CriteriaBuilder cb, Root<AccountForAgent> root) {
		
		var params = new ArrayList<Predicate>();
		
		if(null != createdFrom) {
			params.add(cb.greaterThanOrEqualTo(root.get(AccountForAgent_.createdAt), createdFrom.atStartOfDay()));
		}
		
		if(null != createdTo) {
			params.add(cb.lessThan(root.get(AccountForAgent_.createdAt), createdTo.plusDays(1).atStartOfDay()));
		}
		
		if(StringUtils.hasLength(keyword)) {
			params.add(cb.or(
				cb.like(cb.lower(root.get(AccountForAgent_.phone)), keyword.toLowerCase().concat("%")),
				cb.like(cb.lower(root.get(AccountForAgent_.shopName)), keyword.toLowerCase().concat("%"))
			));
		}

		return params.toArray(size -> new Predicate[size]);
	}	

}
