package com.jdc.agent.api;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jdc.agent.api.input.AgentAccountForm;
import com.jdc.agent.api.input.AgentAccountSearch;
import com.jdc.agent.api.output.AgentAccountInfo;
import com.jdc.agent.ws.AgentAccountManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("account")
public class AccountManagementApi {
	
	private final AgentAccountManagementService service;

	@GetMapping
	List<AgentAccountInfo> search(AgentAccountSearch search) {
		return service.search(search);
	}
	
	@PostMapping
	AgentAccountInfo create(@Validated @RequestBody AgentAccountForm form, BindingResult result) {
		return service.create(form);
	}
	
	@PostMapping("upload")
	List<AgentAccountInfo> upload(@RequestBody MultipartFile file) {
		return service.upload(file);
	}
	
}
