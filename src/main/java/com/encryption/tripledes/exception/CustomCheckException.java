package com.encryption.tripledes.exception;

public class CustomCheckException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomCheckException(String errorMessage, Throwable e) {
		super(errorMessage, e);
	}

}
