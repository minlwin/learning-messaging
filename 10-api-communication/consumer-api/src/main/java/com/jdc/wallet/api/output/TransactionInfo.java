package com.jdc.wallet.api.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.jdc.domain.utils.Status;

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

}
