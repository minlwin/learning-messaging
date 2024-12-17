package com.jdc.core.exceptions.handler;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jdc.core.exceptions.ApiBaseException;

@RestControllerAdvice
public class ApiBaseExceptionHandler {

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	List<String> handle(ApiBaseException exception) {
		return exception.getMessages();
	}
}
