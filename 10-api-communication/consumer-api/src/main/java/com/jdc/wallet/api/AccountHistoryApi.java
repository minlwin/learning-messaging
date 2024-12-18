package com.jdc.wallet.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.wallet.api.output.WalletAccountHistoryInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("history")
public class AccountHistoryApi {

	@GetMapping
	List<WalletAccountHistoryInfo> findAll() {
		return null;
	}
}
