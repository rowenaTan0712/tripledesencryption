package com.encryption.tripledes.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SubscriberDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1774966067486168779L;
	private String accountNo;
	private String lastName;
	private String firstName;
	private String cellNo;
	private String amount;
	
	public SubscriberDTO() {
		
	}

	public SubscriberDTO(String accountNo, String lastName, String firstName, String cellNo, String amount) {
		super();
		this.accountNo = accountNo;
		this.lastName = lastName;
		this.firstName = firstName;
		this.cellNo = cellNo;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "SubscriberDTO [accountNo=" + accountNo + ", lastName=" + lastName + ", firstName=" + firstName
				+ ", cellNo=" + cellNo + ", amount=" + amount + "]";
	}

}
