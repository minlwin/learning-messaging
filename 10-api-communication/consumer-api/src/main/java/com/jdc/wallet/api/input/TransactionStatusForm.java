package com.jdc.wallet.api.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransactionStatusForm(
		@NotBlank(message = "Please enter agent transaction id.")
		String id,
		@NotNull(message = "Please select confirmation status.")
		Boolean confirmed,
		String remark
		) {
}
