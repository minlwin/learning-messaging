package com.jdc.agent.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.agent.api.input.AgentAccountForm;
import com.jdc.agent.api.output.AgentAccountInfo;

@RestController
@RequestMapping("account")
public class AccountManagementApi {

	@GetMapping
	List<AgentAccountInfo> findAll() {
		return null;
	}
	
	AgentAccountInfo create(AgentAccountForm form) {
		return null;
	}
}
