package com.rabobank.custstmt.mockito.testmock;

import static org.junit.Assert.*;

import com.rabobank.custstmt.models.Records;
import com.rabobank.custstmt.models.UploadFileResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import com.rabobank.custstmt.exception.CustStmtException;
import com.rabobank.custstmt.service.CustFileVerifyServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class XMLTestFileRunner {
	@Rule
	public MockitoRule rule = MockitoJUnit.rule();
	@InjectMocks
	private CustFileVerifyServiceImpl custFileVerifyServiceImpl;

	private static List<UploadFileResponse> XMLValuesFactory() {
		List<UploadFileResponse> factoryRecords = new ArrayList<UploadFileResponse>();
		try {
			UploadFileResponse[] uArray = new UploadFileResponse[3];
			UploadFileResponse uploadFileResponse = new UploadFileResponse();
			uploadFileResponse.setReference("ref1");
			uploadFileResponse.setDescription("desc1");
			uploadFileResponse.setAccountNumber("acc1");
			uploadFileResponse.setStartBalance("12.5");
			uploadFileResponse.setMutation("2.5");
			uploadFileResponse.setEndBalance("15.0");
			uArray[0] = uploadFileResponse;
			factoryRecords.add(uArray[0]);
			UploadFileResponse uploadFileResponse1 = new UploadFileResponse();
			uploadFileResponse1.setReference("ref2");
			uploadFileResponse1.setDescription("desc2");
			uploadFileResponse1.setAccountNumber("acc2");
			uploadFileResponse1.setStartBalance("12");
			uploadFileResponse1.setMutation("2");
			uploadFileResponse1.setEndBalance("14");
			uArray[1] = uploadFileResponse1;
			factoryRecords.add(uArray[1]);
			UploadFileResponse uploadFileResponse2 = new UploadFileResponse();
			uploadFileResponse2.setReference("ref3");
			uploadFileResponse2.setDescription("desc3");
			uploadFileResponse2.setAccountNumber("acc3");
			uploadFileResponse2.setStartBalance("0");
			uploadFileResponse2.setMutation("-2.5");
			uploadFileResponse2.setEndBalance("-2.5");
			uArray[2] = uploadFileResponse2;
			factoryRecords.add(uArray[2]);
			Iterator<UploadFileResponse> i = factoryRecords.iterator();
			while (i.hasNext()) {
				UploadFileResponse v = i.next();

			}

		} catch (Exception e) {
			e.getMessage();
		}
		return factoryRecords;
	}

	@Test
	public void withoutDuplicatedReferenceTest() throws CustStmtException {
		Records recordsnew = null;
		UploadFileResponse[] uArray = new UploadFileResponse[1];
		Map<String, String> actualMap = new HashMap<String, String>();
		List<UploadFileResponse> uList = XMLValuesFactory();
		UploadFileResponse uploadFileResponse3 = new UploadFileResponse();
		uploadFileResponse3.setReference("ref4");
		uploadFileResponse3.setDescription("desc4");
		uploadFileResponse3.setAccountNumber("acc4");
		uploadFileResponse3.setStartBalance("0");
		uploadFileResponse3.setMutation("-1");
		uploadFileResponse3.setEndBalance("-1");
		uArray[0] = uploadFileResponse3;
		uList.add(uArray[0]);
		UploadFileResponse[] uploadFileResponse = uList
				.toArray(new UploadFileResponse[uList.size()]);
		recordsnew = new Records(uploadFileResponse);
		actualMap = custFileVerifyServiceImpl.XMLFileValidation(recordsnew);
		assert (actualMap.isEmpty());

	}

	@Test
	public void withDuplicatedReferenceTest() throws CustStmtException {
		Map<String, String> expectedMap = new HashMap<String, String>();
		expectedMap.put("ref2", "desc2");
		Records recordsnew = null;
		UploadFileResponse[] uArray = new UploadFileResponse[1];
		Map<String, String> actualMap = new HashMap<String, String>();
		List<UploadFileResponse> uList = XMLValuesFactory();
		UploadFileResponse uploadFileResponse3 = new UploadFileResponse();
		uploadFileResponse3.setReference("ref2");
		uploadFileResponse3.setDescription("desc2");
		uploadFileResponse3.setAccountNumber("acc4");
		uploadFileResponse3.setStartBalance("0");
		uploadFileResponse3.setMutation("-1");
		uploadFileResponse3.setEndBalance("-1");
		uArray[0] = uploadFileResponse3;
		uList.add(uArray[0]);
		UploadFileResponse[] uploadFileResponse = uList
				.toArray(new UploadFileResponse[uList.size()]);
		recordsnew = new Records(uploadFileResponse);
		actualMap = custFileVerifyServiceImpl.XMLFileValidation(recordsnew);
		assertEquals(expectedMap, actualMap);
	}

	@Test
	public void endBalanceTest() throws CustStmtException {
		Map<String, String> expectedMap = new HashMap<String, String>();
		expectedMap.put("ref5", "desc5");
		Records recordsnew = null;
		UploadFileResponse[] uArray = new UploadFileResponse[1];
		Map<String, String> actualMap = new HashMap<String, String>();
		List<UploadFileResponse> uList = XMLValuesFactory();
		UploadFileResponse uploadFileResponse3 = new UploadFileResponse();
		uploadFileResponse3.setReference("ref5");
		uploadFileResponse3.setDescription("desc5");
		uploadFileResponse3.setAccountNumber("acc5");
		uploadFileResponse3.setStartBalance("0");
		uploadFileResponse3.setMutation("-1");
		uploadFileResponse3.setEndBalance("-10");
		uArray[0] = uploadFileResponse3;
		uList.add(uArray[0]);
		UploadFileResponse[] uploadFileResponse = uList
				.toArray(new UploadFileResponse[uList.size()]);
		recordsnew = new Records(uploadFileResponse);
		actualMap = custFileVerifyServiceImpl.XMLFileValidation(recordsnew);
		assertEquals(expectedMap, actualMap);

	}
}
