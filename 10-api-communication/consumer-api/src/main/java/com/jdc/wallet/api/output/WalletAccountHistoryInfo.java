package com.jdc.wallet.api.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.jdc.domain.entity.AccountForWalletHistory;
import com.jdc.domain.entity.AccountForWalletHistoryPk;
import com.jdc.domain.entity.AccountForWalletHistory_;
import com.jdc.domain.entity.AccountForWallet_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record WalletAccountHistoryInfo(
		AccountForWalletHistoryPk id,
		String phone, 
		String name,
		LocalDateTime issueAt,
		BigDecimal previousAmount,
		BigDecimal transactionAmount,
		BigDecimal currentAmount,
		String particular) {

	public static void select(CriteriaQuery<WalletAccountHistoryInfo> cq, Root<AccountForWalletHistory> root) {
		cq.multiselect(
			root.get(AccountForWalletHistory_.id),
			root.get(AccountForWalletHistory_.account).get(AccountForWallet_.phone),
			root.get(AccountForWalletHistory_.account).get(AccountForWallet_.name),
			root.get(AccountForWalletHistory_.issueAt),
			root.get(AccountForWalletHistory_.previousAmount),
			root.get(AccountForWalletHistory_.transactionAmount),
			root.get(AccountForWalletHistory_.currentAmount),
			root.get(AccountForWalletHistory_.particular)
		);
	}
}
