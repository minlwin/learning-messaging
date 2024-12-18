package com.jdc.agent.api.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AgentAccountInfo(
		UUID id,
		String shopName,
		String phone,
		LocalDateTime createdAt,
		BigDecimal amount) {

}
