package com.encryption.tripledes.service;

import com.encryption.tripledes.dto.SubscriberDTO;
import com.encryption.tripledes.exception.CustomCheckException;

public interface TripleDesEncryption {
	
	public String encrypt(SubscriberDTO subscriber) throws CustomCheckException;
	
	public String encrypt(String data) throws CustomCheckException;
	
	public String decrypt (String encrypted) throws CustomCheckException;
	
	public SubscriberDTO decryptObject(String encrypted) throws CustomCheckException;
}
