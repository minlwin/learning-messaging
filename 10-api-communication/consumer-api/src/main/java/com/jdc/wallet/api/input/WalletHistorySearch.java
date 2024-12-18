package com.jdc.wallet.api.input;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.domain.entity.AccountForWalletHistory;
import com.jdc.domain.entity.AccountForWalletHistory_;
import com.jdc.domain.entity.AccountForWallet_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record WalletHistorySearch(String phone, Boolean credit) {

	public Predicate[] where(CriteriaBuilder cb, Root<AccountForWalletHistory> root) {
		
		var params = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(phone)) {
			params.add(cb.equal(root.get(AccountForWalletHistory_.account).get(AccountForWallet_.phone), phone));
		}
		
		if(null != credit) {
			params.add(cb.equal(root.get(AccountForWalletHistory_.credit), credit));
		}
		
		return params.toArray(size -> new Predicate[size]);
	}	
}
