package com.jdc.agent.api.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.jdc.domain.entity.AccountForAgent;
import com.jdc.domain.entity.AccountForAgent_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record AgentAccountInfo(
		UUID id,
		String shopName,
		String phone,
		LocalDateTime createdAt,
		BigDecimal amount) {

	public static void select(CriteriaQuery<AgentAccountInfo> cq, Root<AccountForAgent> root) {
		cq.multiselect(
			root.get(AccountForAgent_.id),
			root.get(AccountForAgent_.shopName),
			root.get(AccountForAgent_.phone),
			root.get(AccountForAgent_.createdAt),
			root.get(AccountForAgent_.amount)
		);
	}
}
