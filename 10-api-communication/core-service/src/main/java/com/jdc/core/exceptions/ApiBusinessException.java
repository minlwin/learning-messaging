package com.jdc.core.exceptions;

import java.util.List;

public class ApiBusinessException extends ApiBaseException{

	private static final long serialVersionUID = 1L;

	public ApiBusinessException(List<String> messages) {
		super(messages);
	}

	public ApiBusinessException(String message) {
		super(List.of(message));
	}

	public ApiBusinessException(String message, Throwable cause) {
		super(List.of(message), cause);
	}
}
