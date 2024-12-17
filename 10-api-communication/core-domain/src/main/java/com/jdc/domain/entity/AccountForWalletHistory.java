package com.jdc.domain.entity;

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "WALLET_ACCOUNT_HISTORY")
public class AccountForWalletHistory {

	@EmbeddedId
	private AccountForWalletHistoryPk id;
	
	@ManyToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id", insertable = false, updatable = false)
	private AccountForWallet account;
	
	private boolean credit;
	private BigDecimal previousAmount;
	private BigDecimal currentAmount;
	
	private String particular;
	
}
