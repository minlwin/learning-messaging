package com.jdc.learning.message.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.learning.message.api.input.SaleItemForm;
import com.jdc.learning.message.api.input.SaleSearch;
import com.jdc.learning.message.api.output.SaleInfo;
import com.jdc.learning.message.service.SaleService;

@RestController
@RequestMapping("sale")
public class SalesApi {
	
	@Autowired
	private SaleService service;

	@GetMapping
	List<SaleInfo> search(SaleSearch form) {
		return service.search(form);
	}
	
	@PostMapping
	SaleInfo create(@RequestBody SaleItemForm[] items) {
		return service.create(items);
	}

}
