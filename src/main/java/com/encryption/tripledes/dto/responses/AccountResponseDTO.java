package com.encryption.tripledes.dto.responses;

import java.io.Serializable;

import com.encryption.tripledes.utils.APIStatus;
import com.encryption.tripledes.utils.StatusResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class AccountResponseDTO extends StatusResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1774966067486168779L;
	private String key;
	@JsonProperty("account_no")
	private String acctNo;
	@JsonProperty("external_tag")
	private String externalTag;
	private Double amount;
	private String telco;
	@JsonProperty("client_trace_no")
	private String clientTraceNo;
	
	public AccountResponseDTO(APIStatus message, String traceId) {
		super(message, traceId);
	}
	
	public AccountResponseDTO(String key, String acctNo, String externalTag, Double amount, String telco,
			String clientTraceNo, APIStatus message, String traceId) {
		super(message, traceId);
		this.key = key;
		this.acctNo = acctNo;
		this.externalTag = externalTag;
		this.amount = amount;
		this.telco = telco;
		this.clientTraceNo = clientTraceNo;
	}

	@Override
	public String toString() {
		return "{key=" + key + ", acctNo=" + acctNo + ", externalTag=" + externalTag + ", amount="
				+ amount + ", telco=" + telco + ", clientTraceNo=" + clientTraceNo + "}";
	}
	
	
}
