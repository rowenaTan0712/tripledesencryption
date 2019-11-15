package com.encryption.tripledes.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.encryption.tripledes.exceptions.CustomCheckException;

public class UtilityService {
	
	private Logger logger = LogManager.getLogger(UtilityService.class);
	
	public void verifyHeader(String header) throws CustomCheckException {
		if(header.equals("")) {
			logger.error("Error {}", "Header is null");
			throw new CustomCheckException("Header cannot be null");
		}else if(!header.equalsIgnoreCase("application/x-www-form-urlencoded")) {
			logger.error("Error {}", "Header has an invalid value");
			throw new CustomCheckException("Invalid header value");
		}
	}
}
