package com.jdc.progress.utils.exceptions;

import java.util.List;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private List<String> errors;
	
	public ValidationException(List<String> errors) {
		this.errors = errors;
	}
	
	public List<String> getErrors() {
		return errors;
	}
}
