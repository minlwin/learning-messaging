package com.jdc.wallet.api.input;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.domain.entity.AccountForWallet;
import com.jdc.domain.entity.AccountForWallet_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record WalletAccountSearch(		
		LocalDate createdFrom,
		LocalDate createdTo,
		String keyword) {

	public Predicate[] where(CriteriaBuilder cb, Root<AccountForWallet> root) {
		
		var params = new ArrayList<Predicate>();
		
		if(null != createdFrom) {
			params.add(cb.greaterThanOrEqualTo(root.get(AccountForWallet_.createdAt), createdFrom.atStartOfDay()));
		}
		
		if(null != createdTo) {
			params.add(cb.lessThan(root.get(AccountForWallet_.createdAt), createdTo.plusDays(1).atStartOfDay()));
		}
		
		if(StringUtils.hasLength(keyword)) {
			params.add(cb.or(
				cb.like(cb.lower(root.get(AccountForWallet_.phone)), keyword.toLowerCase().concat("%")),
				cb.like(cb.lower(root.get(AccountForWallet_.name)), keyword.toLowerCase().concat("%"))
			));
		}
		
		return params.toArray(size -> new Predicate[size]);
	}	
	
}
