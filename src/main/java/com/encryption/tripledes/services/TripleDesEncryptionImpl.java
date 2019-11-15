package com.encryption.tripledes.services;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.encryption.tripledes.dto.requests.AccountRequestDTO;
import com.encryption.tripledes.dto.responses.AccountResponseDTO;
import com.encryption.tripledes.dto.responses.CommonResponseDTO;
import com.encryption.tripledes.exceptions.CustomCheckException;
import com.encryption.tripledes.utils.APIStatus;
import com.google.gson.Gson;

@Service
public class TripleDesEncryptionImpl implements TripleDesEncryptionService{
	
	@Value("${des.key}")
	private String secretKey;
	
	private MessageDigest md;
    private byte[] mdKey;
    private byte[] keyBytes;
    private SecretKey key;
    private IvParameterSpec iv;
    private Cipher cipher;
    
    private static final String BYTE_FORMAT = "utf-8";
    private static final Logger logger = LogManager.getLogger(TripleDesEncryptionImpl.class.getName());
	
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
	public ResponseEntity<CommonResponseDTO> encrypt(String data) throws CustomCheckException {
		try {
			logger.info("Request {}", data);
			initiateValues();
	        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
	        byte[] dataInBytes = data.getBytes(BYTE_FORMAT);
	        
	        byte[] cipherText = cipher.doFinal(dataInBytes);
	        CommonResponseDTO response = new CommonResponseDTO(Base64.getEncoder().encodeToString(cipherText), 
	        					APIStatus.SUCCESS, UUID.randomUUID().toString());
	        logger.info("Response {}", response.getResponseData());
	        return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			logger.error("Error {}", e.getMessage());
			throw new CustomCheckException("Encryption of String error.", e);
		}
	}
	
	@Override
	public ResponseEntity<CommonResponseDTO> encrypt (AccountRequestDTO account) throws CustomCheckException {
		try {
			logger.info("Request {}", account.toString());
			initiateValues();
	        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
	        
	        account.setKey(secretKey);
	        byte[] subsBytes = account.toString().getBytes("utf-8");
	        
	        byte[] cipherText = cipher.doFinal(subsBytes);
	        CommonResponseDTO response = new CommonResponseDTO(Base64.getEncoder().encodeToString(cipherText),
	        		APIStatus.SUCCESS, UUID.randomUUID().toString());
	        logger.info("Response {}", response.getResponseData());
	        return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			logger.error("Error {}", e.getMessage());
			throw new CustomCheckException("Encryption of Object error.", e);
		}
	}

	@Override
	public ResponseEntity<CommonResponseDTO> decrypt (String encrypted) throws CustomCheckException{
		try {
			logger.info("Request {}", encrypted);
			initiateValues();
	        cipher.init(Cipher.DECRYPT_MODE, key, iv);
	        
	        byte[] encData = Base64.getDecoder().decode(encrypted);
	        byte[] byteText = cipher.doFinal(encData);
	        CommonResponseDTO response = new CommonResponseDTO(new String(byteText, BYTE_FORMAT),
	        		APIStatus.SUCCESS, UUID.randomUUID().toString());
	        logger.info("Response {}", response.getResponseData());
	        return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			logger.error("Error {}", e.getMessage());
			throw new CustomCheckException("Error on decrypting of string.", e);
		}
	}
	
	@Override
	public ResponseEntity<AccountResponseDTO> decryptObject (String encrypted) throws CustomCheckException {
		try {
			logger.info("Request {}", encrypted);
			initiateValues();
	        cipher.init(Cipher.DECRYPT_MODE, key, iv);
	        
	        byte[] encData = Base64.getDecoder().decode(encrypted);
	        byte[] byteText = cipher.doFinal(encData);

	        String json = new String(byteText, BYTE_FORMAT);
	        Gson gson = new Gson();
	        AccountResponseDTO dto = gson.fromJson(json, AccountResponseDTO.class);
	        dto.setMessage(APIStatus.SUCCESS);
	        dto.setTraceId(UUID.randomUUID().toString());
	        logger.info("Response {}", dto.toString());
	        return new ResponseEntity<>(dto, HttpStatus.OK);
		}catch(Exception e) {
			logger.error("Error {}", e.getMessage());
			throw new CustomCheckException("Error on decrypting of object.", e);
		}
	}
}
