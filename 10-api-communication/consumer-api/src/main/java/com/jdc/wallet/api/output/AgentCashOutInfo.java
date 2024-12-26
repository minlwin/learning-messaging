package com.jdc.wallet.api.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.jdc.domain.entity.AgentTransaction;
import com.jdc.domain.utils.TransactionStatus;

public record AgentCashOutInfo(
		String id,
		UUID agentId,
		String agentPhone,
		String agentShop,
		BigDecimal amount,
		LocalDateTime issueAt,
		TransactionStatus status,
		String particular) {

	public static AgentCashOutInfo from(AgentTransaction entity) {
		return new AgentCashOutInfo(entity.getId(), entity.getAgent().getId(), entity.getAgent().getPhone(), entity.getAgent().getShopName(), entity.getAmount(), entity.getCreatedAt(), entity.getStatus(), entity.getParticular());
	}
}
