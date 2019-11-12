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
	private String key;

	@Override
	public String encrypt (SubscriberDTO subscriber) throws CustomCheckException {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
	        byte[] mdKey = md.digest(key.getBytes("utf-8"));
	        byte[] keyBytes = Arrays.copyOf(mdKey, 24);
	        for (int j = 0, k = 16; j < 8;) {
	            keyBytes[k++] = keyBytes[j++];
	        }

	        SecretKey key = new SecretKeySpec(keyBytes, "DESede");
	        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
	        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

	        byte[] subsByte = subscriber.toString().getBytes("utf-8");
	        byte[] cipherText = cipher.doFinal(subsByte);
	        return  Base64.getEncoder().encodeToString(cipherText);
		}catch(Exception e) {
			throw new CustomCheckException("Encryption of Object error.", e);
		}
	}

	@Override
	public String encrypt(String data) throws CustomCheckException {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
	        byte[] mdKey = md.digest(key.getBytes("utf-8"));
	        byte[] keyBytes = Arrays.copyOf(mdKey, 24);
	        for (int j = 0, k = 16; j < 8;) {
	            keyBytes[k++] = keyBytes[j++];
	        }

	        SecretKey key = new SecretKeySpec(keyBytes, "DESede");
	        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
	        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

	        byte[] subsByte = data.getBytes("utf-8");
	        byte[] cipherText = cipher.doFinal(subsByte);
	        return new String (cipherText, "UTF-8");
		}catch(Exception e) {
			throw new CustomCheckException("Encryption of String error.", e);
		}
	}

	@Override
	public String decrypt (String encrypted) throws CustomCheckException{
		try {
			if(encrypted == null){
	            return "";
	        }
			
			MessageDigest md = MessageDigest.getInstance("md5");
	        byte[] mdKey = md.digest(key.getBytes("utf-8"));
	        byte[] keyBytes = Arrays.copyOf(mdKey, 24);
	        for (int j = 0, k = 16; j < 8;) {
	            keyBytes[k++] = keyBytes[j++];
	        }

	        SecretKey key = new SecretKeySpec(keyBytes, "DESede");
	        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
	        Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
	        decipher.init(Cipher.DECRYPT_MODE, key, iv);

	        byte[] byteText = decipher.doFinal(encrypted.getBytes());

	        return new String(byteText, "UTF-8");
		}catch(Exception e) {
			throw new CustomCheckException("Error on decrypting of string.", e);
		}
	}
	
	@Override
	public SubscriberDTO decryptObject (String encrypted) throws CustomCheckException {
		try {
			if(encrypted == null){
	            return null;
	        }
			MessageDigest md = MessageDigest.getInstance("md5");
	        byte[] mdKey = md.digest(key.getBytes("utf-8"));
	        byte[] keyBytes = Arrays.copyOf(mdKey, 24);
	        for (int j = 0, k = 16; j < 8;) {
	            keyBytes[k++] = keyBytes[j++];
	        }

	        SecretKey key = new SecretKeySpec(keyBytes, "DESede");
	        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
	        Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
	        decipher.init(Cipher.DECRYPT_MODE, key, iv);

	        byte[] byteText = decipher.doFinal(encrypted.getBytes("UTF-8"));

	        String json = Base64.getEncoder().encodeToString(byteText);
	        Gson gson = new Gson();
	        return gson.fromJson(json, SubscriberDTO.class);
		}catch(Exception e) {
			throw new CustomCheckException("Error on decrypting of object.", e);
		}
	}

}
