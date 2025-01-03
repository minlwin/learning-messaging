package com.jdc.agent.api;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.agent.api.input.TransactionForm;
import com.jdc.agent.api.input.TransactionSearch;
import com.jdc.agent.api.output.TransactionInfo;
import com.jdc.agent.api.service.TransactionManagementService;
import com.jdc.agent.messages.CashOutInitiationPublisher;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("transaction")
public class TransactionManagementApi {
	
	private final TransactionManagementService service;
	private final CashOutInitiationPublisher initiationPublisher;

	@GetMapping
	List<TransactionInfo> search(TransactionSearch search) {
		return service.search(search);
	}
	
	@PostMapping
	TransactionInfo create(@Validated @RequestBody TransactionForm form, BindingResult result) {
		var info = service.initiate(form);
		
		initiationPublisher.publish(info.id());
		
		return info;
	}
	
	@GetMapping("{id}")
	TransactionInfo findById(@PathVariable String id) {
		return service.findById(id);
	}
}
