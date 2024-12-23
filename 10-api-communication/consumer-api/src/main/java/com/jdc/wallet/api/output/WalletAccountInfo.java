package com.jdc.wallet.api.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.jdc.domain.entity.AccountForWallet;
import com.jdc.domain.entity.AccountForWallet_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record WalletAccountInfo(
		UUID id,
		String name,
		String phone,
		LocalDateTime createdAt,
		BigDecimal amount) {
	
	public static WalletAccountInfo from(AccountForWallet entity) {
		return new WalletAccountInfo(entity.getId(), entity.getName(), entity.getPhone(), entity.getCreatedAt(), entity.getAmount());
	}

	public static void select(CriteriaQuery<WalletAccountInfo> cq, Root<AccountForWallet> root) {
		cq.multiselect(
			root.get(AccountForWallet_.id),
			root.get(AccountForWallet_.name),
			root.get(AccountForWallet_.phone),
			root.get(AccountForWallet_.createdAt),
			root.get(AccountForWallet_.amount)
		);
	}
}
