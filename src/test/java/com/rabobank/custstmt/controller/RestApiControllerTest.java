package com.rabobank.custstmt.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.web.multipart.MultipartFile;

import com.rabobank.custstmt.exception.CustStmtException;
import com.rabobank.custstmt.models.InvalidData;
import com.rabobank.custstmt.service.CustFileVerifyService;

public class RestApiControllerTest {
	@Rule
	public MockitoRule rule = MockitoJUnit.rule();


	@Mock
	private CustFileVerifyService custFileVerifyService;

	private MultipartFile mockMultipart;

	private InputStream mockInputStr;

	@InjectMocks
	private RestApiController controller;

	@Before
	public void setup() throws IOException {
		mockMultipart = Mockito.mock(MultipartFile.class);
		mockInputStr = Mockito.mock(InputStream.class);
		when(mockMultipart.getInputStream())
				.thenReturn(mockInputStr);
		when(mockMultipart.getContentType()).thenReturn("text/csv");
	}

	@Test
    public void testSuccess()
    {
		Map<String, String> failures = getSampleList();
        InvalidData expectedOutput = new InvalidData(failures);
        try {
			when(custFileVerifyService.custFileVerify(mockMultipart, mockMultipart.getContentType(), mockInputStr)).thenReturn(failures);
		} catch (CustStmtException e) {
			e.printStackTrace();
		}
        InvalidData actualOutput=null;;
		try {
			actualOutput = controller.custFileVerify(mockMultipart);
		} catch (CustStmtException e) {
			e.printStackTrace();
		}
        assertThat(actualOutput).isEqualToComparingFieldByFieldRecursively(expectedOutput);
        try {
			verify(custFileVerifyService).custFileVerify(mockMultipart, mockMultipart.getContentType(), mockInputStr);
		} catch (CustStmtException e) {
			e.printStackTrace();
		}
    }
	public static Map<String, String> getSampleList()
    {
		Map<String, String> maps=new HashMap<String,String>();
		maps.put("a2", "a2 description");
		maps.put("a3", "a3 description");
        return maps;
    }
}
