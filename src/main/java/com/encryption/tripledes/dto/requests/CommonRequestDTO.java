package com.encryption.tripledes.dto.requests;

import com.encryption.tripledes.utils.APIStatus;
import com.encryption.tripledes.utils.StatusResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonRequestDTO extends StatusResponse{
	
	public CommonRequestDTO(String data, APIStatus message, String traceId) {
		super(message, traceId);
		this.data = data;
	}

	private String data;

}
