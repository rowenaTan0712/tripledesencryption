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

@SpringBootTest(classes={TripleDesEncryptionImpl.class})
@RunWith(SpringRunner.class)
public class TripleDesEncryptionServiceTest {
	@Autowired
	private TripleDesEncryptionService tripleDesEncryptService;

	private CommonRequestDTO commonRequestDTO;

	private AccountRequestDTO accountRequestDTO;


	@Before
	public void setUp(){
		ReflectionTestUtils.setField(tripleDesEncryptService, "secretKey", "Password123");

		commonRequestDTO = new  CommonRequestDTO("test the string in here 1234566 additional data");

		accountRequestDTO =  new AccountRequestDTO("Password123", "1223123123", "tag-7899", (Double) 4500.00, "smart", "12312312414");
	}

	@Test
	public void testCipherObject () throws CustomCheckException {
		ResponseEntity<CommonResponseDTO> response = tripleDesEncryptService.encrypt(accountRequestDTO);
		ResponseEntity<AccountResponseDTO> acctResponse = tripleDesEncryptService.decryptObject(response.getBody().getResponseData());
		assertThat(accountRequestObject()).isEqualToComparingFieldByField(acctResponse.getBody());
	}

	@Test
	public void testCipherString () throws CustomCheckException {
		ResponseEntity<CommonResponseDTO> response = tripleDesEncryptService.encrypt(stringRequest().getData());
		ResponseEntity<CommonResponseDTO> decryptResponse= tripleDesEncryptService.decrypt(response.getBody().getResponseData());
		assertEquals(stringRequest().getData(), decryptResponse.getBody().getResponseData());
	}
}
