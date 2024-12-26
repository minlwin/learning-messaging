package com.jdc.wallet.api.output;

import java.math.BigDecimal;

import com.jdc.domain.entity.AgentTransaction;
import com.jdc.domain.utils.TransactionStatus;

public record AgentCashOutResult(
		String agentTransactionId,
		String agentShopName,
		String agentPhone,
		BigDecimal amount,
		String particular,
		TransactionStatus status,
		String walletTransactionId
		) {

	public static AgentCashOutResult from(AgentTransaction entity) {
		return new AgentCashOutResult(entity.getId(), entity.getAgent().getShopName(), entity.getAgent().getPhone(), entity.getAmount(), entity.getParticular(), entity.getStatus(), walletTransaction(entity));
	}
	
	private static String walletTransaction(AgentTransaction entity) {
		if(null != entity.getWalletTrx()) {
			return entity.getWalletTrx().getId();
		}
		return null;
	}

}
