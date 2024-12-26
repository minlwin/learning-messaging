package com.jdc.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.jdc.domain.utils.TransactionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "WALLET_TRANSACTION")
public class WalletTransaction {

	@Id
	private String id;
	
	@Column(nullable = false, unique = true)
	private String globalNumber;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private AccountForWallet consumer;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private AccountForAgent agent;
	
	private BigDecimal amount;
	
	@OneToOne
	private AgentTransaction agentTrx;
	
	private TransactionStatus status;
	
	private LocalDateTime createdAt;
	private LocalDateTime finishedAt;
	
	private String remark;
	
}
