package com.encryption.tripledes.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusResponse {
	
	private APIStatus message;
	@JsonProperty("trace_id")
	private String traceId;
	
	public StatusResponse(APIStatus message, String traceId) {
		super();
		this.message = message;
		this.traceId = traceId;
	}

}
