package com.jdc.agent.api.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.jdc.domain.entity.AccountForAgent_;
import com.jdc.domain.entity.AccountForWallet_;
import com.jdc.domain.entity.AgentTransaction;
import com.jdc.domain.entity.AgentTransaction_;
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
	
	public static TransactionInfo from(AgentTransaction entity) {
		return new TransactionInfo(entity.getId(), entity.getGlobalNumber(), entity.getAgent().getId(), entity.getAgent().getPhone(), entity.getAgent().getShopName(), entity.getConsumer().getId(), entity.getConsumer().getPhone(), entity.getConsumer().getName(), entity.getAmount(), entity.getCreatedAt(), entity.getFinishedAt(), entity.getStatus(), entity.getParticular(), entity.getErrorMessage());
	}
	
	public static void select(CriteriaQuery<TransactionInfo> cq, Root<AgentTransaction> root) {
		cq.multiselect(
			root.get(AgentTransaction_.id),
			root.get(AgentTransaction_.id),
			root.get(AgentTransaction_.agent).get(AccountForAgent_.id),
			root.get(AgentTransaction_.agent).get(AccountForAgent_.phone),
			root.get(AgentTransaction_.agent).get(AccountForAgent_.shopName),
			root.get(AgentTransaction_.consumer).get(AccountForWallet_.id),
			root.get(AgentTransaction_.consumer).get(AccountForWallet_.phone),
			root.get(AgentTransaction_.consumer).get(AccountForWallet_.name),
			root.get(AgentTransaction_.amount),
			root.get(AgentTransaction_.createdAt),
			root.get(AgentTransaction_.finishedAt),
			root.get(AgentTransaction_.status),
			root.get(AgentTransaction_.particular),
			root.get(AgentTransaction_.errorMessage)
		);
	}

}
