package com.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(NoSuchElementFoundException.class)
	@ResponseStatus (value = HttpStatus.NOT_FOUND)
	public ExceptionResponse handleException(NoSuchElementFoundException exception) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setErrorCode("Error_Found");
		exceptionResponse.setErrorMessage(exception.getMessage());
		return exceptionResponse;
	}

	
	@ExceptionHandler(Exception.class)
	@ResponseStatus (value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionResponse handleException(Exception exception) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setErrorCode("Error_Server");
		exceptionResponse.setErrorMessage(exception.getMessage());
		return exceptionResponse;
	}

}
