package com.encryption.tripledes.utils;

import lombok.Getter;

@Getter
public enum APIStatus {
	SUCCESS(200, "Successful"),
	INVALID_REQUEST(400, "Invalid Request"),
	INVALID_PARAM_VALUE(401,"Invalid Param Value"),
	INTERNAL_SERVER_ERROR(500, "Invalid Server Error");
	
	private final int status;
	private final String description;
	
	private APIStatus(int status, String description) {
		this.status = status;
		this.description = description;
	}	
}
