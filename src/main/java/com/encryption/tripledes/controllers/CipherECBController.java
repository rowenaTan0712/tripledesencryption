package com.encryption.tripledes.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encryption.tripledes.dto.requests.AccountRequestDTO;
import com.encryption.tripledes.dto.requests.CommonRequestDTO;
import com.encryption.tripledes.dto.responses.AccountResponseDTO;
import com.encryption.tripledes.dto.responses.CommonResponseDTO;
import com.encryption.tripledes.exceptions.CustomCheckException;
import com.encryption.tripledes.services.TripleDesEncryptionECBService;
import com.encryption.tripledes.utils.UtilityService;

@RestController
@RequestMapping("/cipher/ecb")
public class CipherECBController extends UtilityService {
	
	@Autowired
	private TripleDesEncryptionECBService tripleDesEcb;
	
	@PostMapping("/harden")
	public ResponseEntity<CommonResponseDTO> encryptObject ( @RequestHeader(name="X-Header", required = true) String header,
			@RequestBody @Valid AccountRequestDTO account) throws CustomCheckException {
		verifyHeader(header);
		return tripleDesEcb.encrypt(account);
	}
	
	@PostMapping("/soften")
	public ResponseEntity<AccountResponseDTO> decryptObject (@RequestHeader(name="X-Header", required = true) String header, 
			@RequestBody @Valid CommonRequestDTO encrypted) throws CustomCheckException {
		verifyHeader(header);
		return tripleDesEcb.decryptObject(encrypted.getData());
	}
	
	@PostMapping("/harden/value")
	public ResponseEntity<CommonResponseDTO> encryptValue (@RequestHeader(name="X-Header", required = true) String header,
			@RequestBody @Valid CommonRequestDTO key) throws CustomCheckException {
		verifyHeader(header);
		return tripleDesEcb.encrypt(key.getData());
	}
	
	@PostMapping("/soften/value")
	public ResponseEntity<CommonResponseDTO> decryptValue (@RequestHeader(name="X-Header", required = true) String header,
			@RequestBody @Valid CommonRequestDTO encrypted) throws CustomCheckException {
		verifyHeader(header);
		return tripleDesEcb.decrypt(encrypted.getData());
	}
}
