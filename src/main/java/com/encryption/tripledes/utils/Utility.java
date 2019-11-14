package com.encryption.tripledes.utils;

import com.encryption.tripledes.exceptions.CustomCheckException;

public class Utility {
	
	public void verifyHeader(String header) throws CustomCheckException {
		if(header.equals("")) {
			throw new CustomCheckException("Header cannot be null");
		}else if(!header.equalsIgnoreCase("application/x-www-form-urlencoded")) {
			throw new CustomCheckException("Invalid header value");
		}
	}
}
