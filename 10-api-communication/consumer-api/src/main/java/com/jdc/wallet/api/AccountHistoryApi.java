package com.jdc.wallet.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.wallet.api.input.WalletHistorySearch;
import com.jdc.wallet.api.output.WalletAccountHistoryInfo;
import com.jdc.wallet.api.service.AccountHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("history")
public class AccountHistoryApi {
	
	private final AccountHistoryService service;

	@GetMapping
	List<WalletAccountHistoryInfo> search(WalletHistorySearch search) {
		return service.search(search);
	}
}
