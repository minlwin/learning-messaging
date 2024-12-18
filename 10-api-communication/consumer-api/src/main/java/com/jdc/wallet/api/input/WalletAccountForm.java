package com.jdc.wallet.api.input;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WalletAccountForm(
		@NotBlank(message = "Please enter consumer phone.")
		String phone,
		@NotBlank(message = "Please enter consumer name.")
		String name,
		@NotNull(message = "Please enter initial amount for wallet.")
		BigDecimal amount) {

}
