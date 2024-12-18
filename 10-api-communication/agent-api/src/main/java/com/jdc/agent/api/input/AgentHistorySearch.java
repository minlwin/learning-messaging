package com.jdc.agent.api.input;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.domain.entity.AccountForAgentHistory;
import com.jdc.domain.entity.AccountForAgentHistory_;
import com.jdc.domain.entity.AccountForAgent_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record AgentHistorySearch(String phone, Boolean credit) {

	public Predicate[] where(CriteriaBuilder cb, Root<AccountForAgentHistory> root) {
		
		var params = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(phone)) {
			params.add(cb.equal(root.get(AccountForAgentHistory_.account).get(AccountForAgent_.phone), phone));
		}
		
		if(null != credit) {
			params.add(cb.equal(root.get(AccountForAgentHistory_.credit), credit));
		}
		
		return params.toArray(size -> new Predicate[size]);
	}	

}
