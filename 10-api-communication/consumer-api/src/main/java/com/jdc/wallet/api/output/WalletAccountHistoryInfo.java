package com.jdc.wallet.api.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.jdc.domain.entity.AccountForWalletHistoryPk;

public record WalletAccountHistoryInfo(
		AccountForWalletHistoryPk id,
		String phone, 
		String name,
		LocalDateTime issueAt,
		BigDecimal previousAmount,
		BigDecimal transactionAmount,
		BigDecimal currentAmount,
		String particular) {

}
