package com.encryption.tripledes.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encryption.tripledes.dto.RequestDTO;
import com.encryption.tripledes.dto.SubscriberDTO;
import com.encryption.tripledes.exception.CustomCheckException;
import com.encryption.tripledes.service.TripleDesEncryption;

@RestController
@RequestMapping("/encryption")
public class CipherController {
	
	@Autowired
	private TripleDesEncryption tripleDes;
	
	@PostMapping("/harden")
	public String encryptObject (@RequestBody @Valid SubscriberDTO subscriber) throws CustomCheckException {
		return tripleDes.encrypt(subscriber);
	}
	
	@PostMapping("/soften")
	public SubscriberDTO decryptObject (@RequestBody @Valid RequestDTO encrypted) throws CustomCheckException {
		return tripleDes.decryptObject(encrypted.getData());
	}
	
	@PostMapping("/harden/value")
	public String encryptValue (@RequestBody @Valid RequestDTO key) throws CustomCheckException {
		return tripleDes.encrypt(key.getData());
	}
	
	@PostMapping("/soften/value")
	public String decryptValue (@RequestBody @Valid RequestDTO encrypted) throws CustomCheckException {
		return tripleDes.decrypt(encrypted.getData());
	}
}
