package com.jdc.agent.api.input;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AgentAccountForm(
		@NotBlank(message = "Please enter agent phone.")
		String phone,
		@NotBlank(message = "Please enter agent shop name.")
		String shopName,
		@NotNull(message = "Please enter initial amount for agent.")
		BigDecimal amount) {

}
