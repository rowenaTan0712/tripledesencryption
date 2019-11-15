package com.encryption.tripledes.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonRequestDTO{
	
	private String data;
	
	public CommonRequestDTO() {
	}
	
	public CommonRequestDTO(String data) {
		this.data = data;
	}
}
