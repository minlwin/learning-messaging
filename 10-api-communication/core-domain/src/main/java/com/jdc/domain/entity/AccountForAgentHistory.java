package com.jdc.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "AGENT_ACCOUNT_HISTORY")
public class AccountForAgentHistory {

	@EmbeddedId
	private AccountForAgentHistoryPk id;
	
	@ManyToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id", insertable = false, updatable = false)
	private AccountForAgent account;
	
	private boolean credit;
	private BigDecimal previousAmount;
	private BigDecimal transactionAmount;
	private BigDecimal currentAmount;

	private LocalDateTime issueAt;
	private String particular;
}
