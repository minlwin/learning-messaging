package com.jdc.learning.message.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.learning.message.api.input.ProductSearch;
import com.jdc.learning.message.api.output.ProductInfo;
import com.jdc.learning.message.service.ProductService;

@RestController
@RequestMapping("product")
public class ProductApi {
	
	@Autowired
	private ProductService service;

	@GetMapping
	List<ProductInfo> search(ProductSearch form) {
		return service.search(form);
	}
}
