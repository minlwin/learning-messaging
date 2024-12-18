package com.jdc.wallet.api.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record WalletAccountInfo(
		UUID id,
		String name,
		String phone,
		LocalDateTime createdAt,
		BigDecimal amount) {

}
