package com.encryption.tripledes.services;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

@Service
public class TripleDesEncryptionECBImpl implements TripleDesEncryptionECBService{
	
	@Value("${des.key}")
	private String secretKey;
	
	private MessageDigest md;
    private byte[] mdKey;
    private byte[] keyBytes;
    private SecretKey key;
    private Cipher cipher;
    
    private static final String BYTE_FORMAT = "utf-8";
    private static final Logger logger = LogManager.getLogger(TripleDesEncryptionECBImpl.class.getName());
	
	public void initiateValues () throws Exception{
		 md = MessageDigest.getInstance("md5");
		 SimpleDateFormat df = new SimpleDateFormat("MMddyyyy");
	     String date = df.format(new Date());
		 String keyVal = secretKey+date;
		 mdKey = md.digest(keyVal.getBytes("utf-8"));
		 keyBytes = Arrays.copyOf(mdKey, 24);
		 for (int j = 0, k = 16; j < 8;) {
            keyBytes[k++] = keyBytes[j++];
		 }
		 
		 key = new SecretKeySpec(keyBytes, "DESede");
		 cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
	}
	
	@Override
	public ResponseEntity<CommonResponseDTO> encrypt(String data) throws CustomCheckException {
		try {
			logger.info("Request {}", data);
			initiateValues();
	        cipher.init(Cipher.ENCRYPT_MODE, key);
	        byte[] dataInBytes = data.getBytes(BYTE_FORMAT);
	        
	        byte[] cipherText = cipher.doFinal(dataInBytes);
	        CommonResponseDTO response = new CommonResponseDTO(Base64.getMimeEncoder().encodeToString(cipherText), 
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
	        cipher.init(Cipher.ENCRYPT_MODE, key);
	        
	        byte[] subsBytes = account.toString().getBytes("utf-8");
	        byte[] cipherText = cipher.doFinal(subsBytes);
	        CommonResponseDTO response = new CommonResponseDTO(Base64.getMimeEncoder().encodeToString(cipherText),
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
	        cipher.init(Cipher.DECRYPT_MODE, key);
	        
	        byte[] encData = Base64.getMimeDecoder().decode(encrypted);
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
	        cipher.init(Cipher.DECRYPT_MODE, key);
	        
	        byte[] encData = Base64.getMimeDecoder().decode(encrypted);
	        byte[] byteText = cipher.doFinal(encData);

	        String json = new String(byteText, BYTE_FORMAT);
	        Map<String, String> jsonMap = toMap(json);
 	        AccountResponseDTO dto =  new AccountResponseDTO(APIStatus.SUCCESS, UUID.randomUUID().toString());
 	        dto.setKey(jsonMap.get("key"));
 	        dto.setAcctNo(jsonMap.get("acctNo"));
 	        dto.setExternalTag(jsonMap.get("externalTag"));
 	        dto.setAmount(Double.parseDouble(jsonMap.get("amount")));
 	        dto.setTelco(jsonMap.get("telco"));
 	        dto.setClientTraceNo(jsonMap.get("clientTraceNo"));
	        logger.info("Response {}", dto.toString());
	        return new ResponseEntity<>(dto, HttpStatus.OK);
		}catch(Exception e) {
			logger.error("Error {}", e.getMessage());
			throw new CustomCheckException("Error on decrypting of object.", e);
		}
	}
	
	public Map<String, String> toMap (String json){
		json = json.substring(1,json.length()-1);
        String[] jsonArr = json.split(",");
        Map<String, String> jsonMap = new HashMap<String, String>();
        for (int i=0; i<jsonArr.length; i++) {
            String pair = jsonArr[i].trim();
            if(pair.contains("key")) {
            	String[] keyPair = pair.split("key");
            	jsonMap.put("key", keyPair[1].substring(1, keyPair[1].length()));
            }else {
            	String[] keyValue = pair.split("=");
            	jsonMap.put(keyValue[0], keyValue[1]);
            }
        }
        return jsonMap;
	}
}
