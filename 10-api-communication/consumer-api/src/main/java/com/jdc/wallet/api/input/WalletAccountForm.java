package com.jdc.wallet.api.input;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.jdc.domain.entity.AccountForWallet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WalletAccountForm(
		@NotBlank(message = "Please enter consumer phone.")
		String phone,
		@NotBlank(message = "Please enter consumer name.")
		String name,
		@NotNull(message = "Please enter initial amount for wallet.")
		BigDecimal amount) {

	public AccountForWallet entity() {
		var entity = new AccountForWallet();
		entity.setName(name);
		entity.setPhone(phone);
		entity.setAmount(amount);
		entity.setCreatedAt(LocalDateTime.now());
		return entity;
	}
}
