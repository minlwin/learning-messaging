package com.jdc.agent.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.agent.api.input.AgentHistorySearch;
import com.jdc.agent.api.output.AgentAccountHistoryInfo;
import com.jdc.agent.ws.AgentAccountHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("history")
public class AccountHistoryApi {
	
	private final AgentAccountHistoryService service;

	@GetMapping
	List<AgentAccountHistoryInfo> search(AgentHistorySearch search) {
		return service.search(search);
	}
}
