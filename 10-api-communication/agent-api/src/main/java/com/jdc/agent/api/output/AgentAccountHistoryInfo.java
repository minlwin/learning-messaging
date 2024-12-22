package com.jdc.agent.api.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.jdc.domain.entity.AccountForAgentHistory;
import com.jdc.domain.entity.AccountForAgentHistoryPk;
import com.jdc.domain.entity.AccountForAgentHistory_;
import com.jdc.domain.entity.AccountForAgent_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record AgentAccountHistoryInfo(
		AccountForAgentHistoryPk id,
		String phone, 
		String shopName,
		LocalDateTime issueAt,
		BigDecimal previousAmount,
		BigDecimal transactionAmount,
		BigDecimal currentAmount,
		String particular) {

	public static void select(CriteriaQuery<AgentAccountHistoryInfo> cq, Root<AccountForAgentHistory> root) {
		cq.multiselect(
			root.get(AccountForAgentHistory_.id),
			root.get(AccountForAgentHistory_.account).get(AccountForAgent_.phone),
			root.get(AccountForAgentHistory_.account).get(AccountForAgent_.shopName),
			root.get(AccountForAgentHistory_.issueAt),
			root.get(AccountForAgentHistory_.previousAmount),
			root.get(AccountForAgentHistory_.transactionAmount),
			root.get(AccountForAgentHistory_.currentAmount),
			root.get(AccountForAgentHistory_.particular)
		);
	}
}
