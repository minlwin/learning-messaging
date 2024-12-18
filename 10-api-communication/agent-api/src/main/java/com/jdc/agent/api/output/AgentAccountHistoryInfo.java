package com.jdc.agent.api.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.jdc.domain.entity.AccountForAgentHistoryPk;

public record AgentAccountHistoryInfo(
		AccountForAgentHistoryPk id,
		String phone, 
		String shopName,
		LocalDateTime issueAt,
		BigDecimal previousAmount,
		BigDecimal transactionAmount,
		BigDecimal currentAmount,
		String particular) {

}
