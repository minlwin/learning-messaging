package com.jdc.wallet.api.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.jdc.domain.entity.AccountForAgent_;
import com.jdc.domain.entity.AccountForWallet_;
import com.jdc.domain.entity.AgentTransaction_;
import com.jdc.domain.entity.WalletTransaction;
import com.jdc.domain.entity.WalletTransaction_;
import com.jdc.domain.utils.Status;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record TransactionInfo(
		String id,
		String globalTransactionId,
		UUID agentId,
		String agentPhone,
		String agentShop,
		UUID consumerId,
		String consumerPhone,
		String consumerName,
		BigDecimal amount,
		LocalDateTime issueAt,
		LocalDateTime finishAt,
		Status status,
		String particular,
		String errorMessage) {
	
	public static TransactionInfo from(WalletTransaction entity) {
		return new TransactionInfo(entity.getId(), entity.getGlobalNumber(), entity.getAgent().getId(), entity.getAgent().getPhone(), entity.getAgent().getShopName(), entity.getConsumer().getId(), entity.getConsumer().getPhone(), entity.getConsumer().getName(), entity.getAmount(), entity.getCreatedAt(), entity.getFinishedAt(), entity.getStatus(), entity.getAgentTrx().getParticular(), entity.getAgentTrx().getErrorMessage());
	}

	public static void select(CriteriaQuery<TransactionInfo> cq, Root<WalletTransaction> root) {
		cq.multiselect(
			root.get(WalletTransaction_.id),
			root.get(WalletTransaction_.globalNumber),
			root.get(WalletTransaction_.agent).get(AccountForAgent_.id),
			root.get(WalletTransaction_.agent).get(AccountForAgent_.phone),
			root.get(WalletTransaction_.agent).get(AccountForAgent_.shopName),
			root.get(WalletTransaction_.consumer).get(AccountForWallet_.id),
			root.get(WalletTransaction_.consumer).get(AccountForWallet_.phone),
			root.get(WalletTransaction_.consumer).get(AccountForWallet_.name),
			root.get(WalletTransaction_.amount),
			root.get(WalletTransaction_.createdAt),
			root.get(WalletTransaction_.finishedAt),
			root.get(WalletTransaction_.status),
			root.get(WalletTransaction_.agentTrx).get(AgentTransaction_.particular),
			root.get(WalletTransaction_.agentTrx).get(AgentTransaction_.errorMessage)
		);
	}
}
