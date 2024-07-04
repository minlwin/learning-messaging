package com.jdc.learning.message.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.learning.message.api.input.InventorySearch;
import com.jdc.learning.message.api.output.InventoryInfo;
import com.jdc.learning.message.service.InventoryService;

@RestController
@RequestMapping("inventory")
public class InventoryApi {
	
	@Autowired
	private InventoryService service;

	@GetMapping
	List<InventoryInfo> search(InventorySearch form) {
		return service.search(form);
	}
}
