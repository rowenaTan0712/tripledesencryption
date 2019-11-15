package com.encryption.tripledes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.encryption.tripledes.dto.requests.AccountRequestDTO;
import com.encryption.tripledes.dto.requests.CommonRequestDTO;
import com.encryption.tripledes.dto.responses.AccountResponseDTO;
import com.encryption.tripledes.dto.responses.CommonResponseDTO;
import com.encryption.tripledes.exceptions.CustomCheckException;
import com.encryption.tripledes.services.TripleDesEncryptionImpl;
import com.encryption.tripledes.services.TripleDesEncryptionService;

@RunWith(SpringRunner.class)
public class TripleDesEncryptionServiceTest {	
	@Autowired
	private TripleDesEncryptionService tripleDesEncryptService = new TripleDesEncryptionImpl();
	
	@Before
	public AccountRequestDTO accountRequestObject () {
		return new AccountRequestDTO("Password123", "1223123123", "tag-7899", (Double) 4500.00, "smart", "12312312414");
	}
	
	@Before
	public CommonRequestDTO stringRequest () {
		return new CommonRequestDTO("test the string in here 1234566 additional data");
	}
	
	@Test
	public void testCipherObject () {
		try {
			AccountRequestDTO request = accountRequestObject();
			ReflectionTestUtils.setField(tripleDesEncryptService, "secretKey", "Password123");
			ResponseEntity<CommonResponseDTO> response = tripleDesEncryptService.encrypt(request);
			String encrypted = response.getBody().getResponseData();
			ResponseEntity<AccountResponseDTO> acctResponse = tripleDesEncryptService.decryptObject(encrypted);
			assertThat(accountRequestObject()).isEqualToComparingFieldByField(acctResponse.getBody());
		} catch (CustomCheckException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCipherString () {
		try {
			ReflectionTestUtils.setField(tripleDesEncryptService, "secretKey", "Password123");
			ResponseEntity<CommonResponseDTO> response = tripleDesEncryptService.encrypt(stringRequest().getData());
			String encrypted = response.getBody().getResponseData();
			ResponseEntity<CommonResponseDTO> decryptResponse= tripleDesEncryptService.decrypt(encrypted);
			assertEquals(stringRequest().getData(), decryptResponse.getBody().getResponseData());
		} catch (CustomCheckException e) {
			e.printStackTrace();
		}
	}
}
