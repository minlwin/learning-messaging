package com.jdc.wallet.api.input;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.domain.entity.AccountForWallet_;
import com.jdc.domain.entity.WalletTransaction;
import com.jdc.domain.entity.WalletTransaction_;
import com.jdc.domain.utils.TransactionStatus;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record TransactionSearch(LocalDate from, LocalDate to, String phone, TransactionStatus status) {

	public Predicate[] where(CriteriaBuilder cb, Root<WalletTransaction> root) {
		
		var params = new ArrayList<Predicate>();
		
		if(null != from) {
			params.add(cb.greaterThanOrEqualTo(root.get(WalletTransaction_.createdAt), from.atStartOfDay()));
		}
		
		if(null != to) {
			params.add(cb.lessThan(root.get(WalletTransaction_.createdAt), to.plusDays(1).atStartOfDay()));
		}
		
		if(StringUtils.hasLength(phone)) {
			params.add(cb.equal(root.get(WalletTransaction_.consumer).get(AccountForWallet_.phone), phone));
		}
		
		if(null != status) {
			params.add(cb.equal(root.get(WalletTransaction_.status), status));
		}
		
		return params.toArray(size -> new Predicate[size]);
	}
}
