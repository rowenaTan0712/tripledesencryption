package com.encryption.tripledes.exception;

public class CustomUncheckException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomUncheckException(String errorMessage, Throwable e) {
		super(errorMessage, e);
	}

}
