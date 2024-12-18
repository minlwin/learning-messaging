package com.jdc.wallet.api;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jdc.wallet.api.input.WalletAccountForm;
import com.jdc.wallet.api.output.WalletAccountInfo;
import com.jdc.wallet.service.AccountManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("account")
public class AccountManagementApi {
	
	private final AccountManagementService service;

	@GetMapping
	List<WalletAccountInfo> findAll() {
		return service.findAll();
	}
	
	@PostMapping
	WalletAccountInfo create(@Validated @RequestBody WalletAccountForm form, BindingResult result) {
		return service.create(form);
	}
	
	@PostMapping("upload")
	List<WalletAccountInfo> upload(@RequestParam MultipartFile file) {
		return service.upload(file);
	}
}
