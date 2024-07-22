package com.jdc.progress.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.progress.api.input.EscInvoiceSearch;
import com.jdc.progress.api.output.EscInvoiceInfo;
import com.jdc.progress.mode.PageResult;

@RestController
@RequestMapping("invoice")
public class EscInvoiceApi {

	@GetMapping
	PageResult<EscInvoiceInfo> search(EscInvoiceSearch form, 
			@RequestParam(required = false, defaultValue = "0") int page, 
			@RequestParam(required = false, defaultValue = "10") int size) {
		return null;
	}
}
