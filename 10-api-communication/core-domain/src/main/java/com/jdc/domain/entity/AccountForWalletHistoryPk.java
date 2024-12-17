package com.jdc.domain.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AccountForWalletHistoryPk {

	@Column(nullable = false, name = "account_id")
	private UUID accountId;
	private int version;

}
