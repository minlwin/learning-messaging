package com.jdc.wallet.api;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.wallet.api.input.AgentCashOutSearch;
import com.jdc.wallet.api.input.TransactionStatusForm;
import com.jdc.wallet.api.output.AgentCashOutInfo;
import com.jdc.wallet.api.output.AgentCashOutResult;
import com.jdc.wallet.api.service.AgentTransactionService;
import com.jdc.wallet.messages.CashOutConfirmationPublisher;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("cashout/agent")
public class AgentCashOutApi {
	
	private final AgentTransactionService service;
	private final CashOutConfirmationPublisher publisher;

	@GetMapping
	AgentCashOutInfo getCashOutInfo(AgentCashOutSearch search) {
		return service.search(search);
	}
	
	@PostMapping
	AgentCashOutResult confirm(
			@Validated @RequestBody TransactionStatusForm form, BindingResult result) {
		var info = service.confirm(form);
		publisher.publish(form.id());
		return info;
	}
}
