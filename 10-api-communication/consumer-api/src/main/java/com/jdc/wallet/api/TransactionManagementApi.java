package com.jdc.wallet.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.wallet.api.output.TransactionInfo;
import com.jdc.wallet.service.TransactionManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("transaction")
public class TransactionManagementApi {
	
	private final TransactionManagementService service;

	@GetMapping
	List<TransactionInfo> findAll() {
		return service.findAll();
	}
	
	@GetMapping("{id}")
	TransactionInfo findById(@PathVariable String id) {
		return service.findById(id);
	}
}
