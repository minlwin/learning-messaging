package com.jdc.learning.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.learning.model.entity.ProgressHistory;
import com.jdc.learning.model.io.InitiationForm;
import com.jdc.learning.model.io.InitiationResult;
import com.jdc.learning.model.service.ProgressHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("progress")
@RequiredArgsConstructor
public class ProgressApi {
	
	private final ProgressHistoryService service;

	@GetMapping
	public List<ProgressHistory> findAll() {
		return service.findAll();
	}
	
	@PostMapping
	public InitiationResult initiate(@RequestBody InitiationForm form) {
		return service.initiate(form);
	}
}
