package com.encryption.tripledes.service;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.encryption.tripledes.dto.SubscriberDTO;
import com.encryption.tripledes.exception.CustomCheckException;
import com.google.gson.Gson;

@Service
public class TripleDesEncryptionImpl implements TripleDesEncryption{
	
	@Value("${des.key}")
	private String secretKey;
	
	private MessageDigest md;
    private byte[] mdKey;
    private byte[] keyBytes;
    private SecretKey key;
    private IvParameterSpec iv;
    private Cipher cipher;
    
    private static final String BYTE_FORMAT = "utf-8";
	
	public void initiateValues () throws Exception{
		 md = MessageDigest.getInstance("md5");
		 mdKey = md.digest(secretKey.getBytes("utf-8"));
		 keyBytes = Arrays.copyOf(mdKey, 24);
		 for (int j = 0, k = 16; j < 8;) {
            keyBytes[k++] = keyBytes[j++];
		 }
		 
		 key = new SecretKeySpec(keyBytes, "DESede");
		 iv = new IvParameterSpec(new byte[8]);
		 cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
	}
	
	@Override
	public String encrypt(String data) throws CustomCheckException {
		try {
			initiateValues();
	        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
	        byte[] dataInBytes = data.getBytes(BYTE_FORMAT);
	        
	        byte[] cipherText = cipher.doFinal(dataInBytes);
	        return Base64.getEncoder().encodeToString(cipherText);
		}catch(Exception e) {
			throw new CustomCheckException("Encryption of String error.", e);
		}
	}

	@Override
	public String decrypt (String encrypted) throws CustomCheckException{
		try {
			initiateValues();
	        cipher.init(Cipher.DECRYPT_MODE, key, iv);
	        
	        byte[] encData = Base64.getDecoder().decode(encrypted);
	        byte[] byteText = cipher.doFinal(encData);
	        return new String(byteText, BYTE_FORMAT);
		}catch(Exception e) {
			throw new CustomCheckException("Error on decrypting of string.", e);
		}
	}
	
	@Override
	public String encrypt (SubscriberDTO subscriber) throws CustomCheckException {
		try {
			initiateValues();
	        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
	        
	        subscriber.setKey(secretKey);
	        byte[] subsBytes = subscriber.toString().getBytes("utf-8");
	        
	        byte[] cipherText = cipher.doFinal(subsBytes);
	        return Base64.getEncoder().encodeToString(cipherText);
		}catch(Exception e) {
			throw new CustomCheckException("Encryption of Object error.", e);
		}
	}

	@Override
	public SubscriberDTO decryptObject (String encrypted) throws CustomCheckException {
		try {
			initiateValues();
	        cipher.init(Cipher.DECRYPT_MODE, key, iv);
	        
	        byte[] encData = Base64.getDecoder().decode(encrypted);
	        byte[] byteText = cipher.doFinal(encData);

	        String json = new String(byteText, BYTE_FORMAT);
	        Gson gson = new Gson();
	        return gson.fromJson(json, SubscriberDTO.class);
		}catch(Exception e) {
			throw new CustomCheckException("Error on decrypting of object.", e);
		}
	}
}
