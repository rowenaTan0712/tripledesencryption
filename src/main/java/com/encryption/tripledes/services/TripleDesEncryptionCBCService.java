package com.encryption.tripledes.services;

import org.springframework.http.ResponseEntity;

import com.encryption.tripledes.dto.requests.AccountRequestDTO;
import com.encryption.tripledes.dto.responses.AccountResponseDTO;
import com.encryption.tripledes.dto.responses.CommonResponseDTO;
import com.encryption.tripledes.exceptions.CustomCheckException;

public interface TripleDesEncryptionCBCService {
	
	public ResponseEntity<CommonResponseDTO> encrypt(AccountRequestDTO account) throws CustomCheckException;
	
	public ResponseEntity<AccountResponseDTO> decryptObject(String encrypted) throws CustomCheckException;
	
	public ResponseEntity<CommonResponseDTO> encrypt(String data) throws CustomCheckException;
	
	public ResponseEntity<CommonResponseDTO> decrypt (String encrypted) throws CustomCheckException;
}
