package com.jdc.progress.utils.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	List<String> handle(ValidationException e) {
		return e.getErrors();
	}
	
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	List<String> handle(Exception e) {
		e.printStackTrace();
		return List.of(e.getMessage());
	}
}
