package com.jdc.core.exceptions.handler;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;

import com.jdc.core.exceptions.ApiValidationException;

@Aspect
@Configuration
public class ApiValidationExceptionAspect {

	@Pointcut("within(com.jdc.*.api.*) and @annotation(org.springframework.web.bind.annotation.RestController)")
	public void apiMethod() {}
	
	@Before(value = "apiMethod() and args(.., result)", argNames = "result")
	public void before(BindingResult result) {
		
		if(result.hasErrors()) {
			throw new ApiValidationException(result.getFieldErrors().stream().map(a -> a.getDefaultMessage()).toList());
		}
	}
}
