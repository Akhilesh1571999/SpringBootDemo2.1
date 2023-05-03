package com.project.exception;

public class NoSuchElementFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public NoSuchElementFoundException(String message) {
		super(message);
	}

}
