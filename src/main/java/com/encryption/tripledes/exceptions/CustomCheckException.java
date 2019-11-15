package com.encryption.tripledes.exceptions;

public class CustomCheckException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomCheckException(String errorMessage, Throwable e) {
		super(errorMessage, e);
	}
	
	public CustomCheckException(String errorMessage) {
		super(errorMessage);
	}

}
