package com.jdc.learning.message.utils;

public class ApiExcception extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApiExcception(String message) {
		super(message);
	}
}
