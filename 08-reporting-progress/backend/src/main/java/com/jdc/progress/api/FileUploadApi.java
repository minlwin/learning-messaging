package com.jdc.progress.api;

import java.util.List;

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
import com.jdc.progress.api.output.EscUploadHistoryListItem;
import com.jdc.progress.api.output.EscUploadResult;
import com.jdc.progress.mode.PageResult;
import com.jdc.progress.service.FileUploadService;

@RestController
@RequestMapping("upload")
public class FileUploadApi {
	
	@Autowired
	private FileUploadService service;

	@PostMapping
	EscUploadResult upload(@Validated EscUploadForm form, BindingResult result) {
		
		if(result.hasErrors()) {
			// Throw Exception
		}
		
		return service.upload(form);
	}
	
	@GetMapping
	PageResult<EscUploadHistoryListItem> search(EscUploadHistorySearch form,
			@RequestParam(required = false, defaultValue = "0") int page, 
			@RequestParam(required = false, defaultValue = "10") int size) {
		return null;
	}
	
	@GetMapping("error/{history}")
	List<EscUploadErrorInfo> searchError(@PathVariable("history") String historyId, 
			@RequestParam(required = false, defaultValue = "0") int page, 
			@RequestParam(required = false, defaultValue = "10") int size) {
		return null;
	}
	
}
