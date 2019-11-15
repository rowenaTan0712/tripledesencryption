package com.encryption.tripledes.dto.responses;

import com.encryption.tripledes.utils.APIStatus;
import com.encryption.tripledes.utils.StatusResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponseDTO extends StatusResponse{
	
	public CommonResponseDTO(String responseData, APIStatus message, String traceId) {
		super(message, traceId);
		this.responseData = responseData;
	}
	
	@JsonProperty("response_data")
	private String responseData;

}
