package com.encryption.tripledes.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class SubscriberDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1774966067486168779L;
	private String key;
	private String acctNo;
	private String externalTag;
	private Double amount;
	private String telco;
	private String clientTraceNo;
	
	public SubscriberDTO() {
		
	}
	
	public SubscriberDTO(String key, String acctNo, String externalTag, Double amount, String telco,
			String clientTraceNo) {
		super();
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
