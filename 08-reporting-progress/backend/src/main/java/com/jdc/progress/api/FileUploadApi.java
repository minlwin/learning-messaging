package com.jdc.progress.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.progress.api.input.EscUploadForm;
import com.jdc.progress.api.input.EscUploadHistorySearch;
import com.jdc.progress.api.output.EscUploadErrorInfo;
import com.jdc.progress.api.output.EscUploadHistoryDetails;
import com.jdc.progress.api.output.EscUploadHistoryListItem;
import com.jdc.progress.api.output.EscUploadResult;
import com.jdc.progress.model.PageResult;
import com.jdc.progress.service.EscInvoiceErrorService;
import com.jdc.progress.service.EscUploadHistoryService;
import com.jdc.progress.service.FileUploadService;
import com.jdc.progress.utils.exceptions.ValidationException;

@RestController
@RequestMapping("upload")
public class FileUploadApi {
	
	@Autowired
	private FileUploadService uploadService;
	
	@Autowired
	private EscUploadHistoryService historyService;
	
	@Autowired
	private EscInvoiceErrorService errorService;

	@PostMapping
	EscUploadResult upload(@Validated EscUploadForm form, BindingResult result) {
		
		if(result.hasErrors()) {
			throw new ValidationException(result.getFieldErrors().stream().map(a -> a.getDefaultMessage()).toList());
		}
		
		return uploadService.upload(form);
	}
	
	@GetMapping("{id}")
	EscUploadHistoryDetails findById(@PathVariable("id") String id) {
		return uploadService.findById(id);
	}
	
	@GetMapping
	PageResult<EscUploadHistoryListItem> search(EscUploadHistorySearch form,
			@RequestParam(required = false, defaultValue = "0") int page, 
			@RequestParam(required = false, defaultValue = "10") int size) {
		return historyService.search(form, page, size);
	}
	
	@GetMapping("error")
	PageResult<EscUploadErrorInfo> searchError(@RequestParam("history") String historyId, 
			@RequestParam(required = false, defaultValue = "0") int page, 
			@RequestParam(required = false, defaultValue = "10") int size) {
		return errorService.searchError(historyId, page, size);
	}
	
}
