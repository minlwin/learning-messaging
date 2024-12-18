package com.jdc.agent.api.input;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.domain.entity.AccountForAgent_;
import com.jdc.domain.entity.AgentTransaction;
import com.jdc.domain.entity.AgentTransaction_;
import com.jdc.domain.utils.Status;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record TransactionSearch(LocalDate from, LocalDate to, String phone, Status status) {

	public Predicate[] where(CriteriaBuilder cb, Root<AgentTransaction> root) {
		
		var params = new ArrayList<Predicate>();
		
		if(null != from) {
			params.add(cb.greaterThanOrEqualTo(root.get(AgentTransaction_.createdAt), from.atStartOfDay()));
		}
		
		if(null != to) {
			params.add(cb.lessThan(root.get(AgentTransaction_.createdAt), to.plusDays(1).atStartOfDay()));
		}

		if(StringUtils.hasLength(phone)) {
			params.add(cb.equal(root.get(AgentTransaction_.agent).get(AccountForAgent_.phone), phone));
		}
		
		if(null != status) {
			params.add(cb.equal(root.get(AgentTransaction_.status), status));
		}
		
		return params.toArray(size -> new Predicate[size]);
	}	

}
