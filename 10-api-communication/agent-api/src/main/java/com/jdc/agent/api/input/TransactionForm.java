package com.jdc.agent.api.input;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransactionForm(
		@NotBlank(message = "Please enter agent phone number.")
		String agent, 
		@NotBlank(message = "Please enter consumer phone number.")
		String consumer,
		@NotNull(message = "Please enter cash out amount.")
		BigDecimal amount, 
		@NotBlank(message = "Please enter particular message.")
		String particular) {

}
