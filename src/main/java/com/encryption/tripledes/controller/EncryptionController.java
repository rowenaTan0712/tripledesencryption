package com.encryption.tripledes.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encryption.tripledes.dto.SubscriberDTO;
import com.encryption.tripledes.exception.CustomCheckException;
import com.encryption.tripledes.service.TripleDesEncryption;

@RestController
@RequestMapping("/encryption")
public class EncryptionController {
	
	@Autowired
	private TripleDesEncryption tripleDes;
	
	@PostMapping("/harden")
	public String encryptObject (@RequestBody @Valid SubscriberDTO subscriber) throws CustomCheckException {
		return tripleDes.encrypt(subscriber);
	}
	
	@PostMapping("/soften")
	public SubscriberDTO decryptObject (@RequestBody @Valid String encrypted) throws CustomCheckException {
		return tripleDes.decryptObject(encrypted);
	}
	
	@PostMapping("/harden/value")
	public String encryptValue (@RequestBody @Valid String key) throws CustomCheckException {
		return tripleDes.encrypt(key);
	}
	
	@PostMapping("/soften/value")
	public String decryptValue (@RequestBody @Valid String encrypted) throws CustomCheckException {
		return tripleDes.decrypt(encrypted);
	}
}
