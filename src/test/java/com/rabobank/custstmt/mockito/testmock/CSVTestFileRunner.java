package com.rabobank.custstmt.mockito.testmock;

import static org.junit.Assert.*;

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
public class CSVTestFileRunner {
	@Rule
	public MockitoRule rule = MockitoJUnit.rule();
	@InjectMocks
	private CustFileVerifyServiceImpl custFileVerifyServiceImpl;

	private static List<String[]> csvValuesFactory() {
		List<String[]> csvdata = new ArrayList<String[]>();
		try {
			String[] strArray1 = { "Reference", "AccountNumber", "Description",
					"Start Balance", "Mutation", "End Balance" };
			String[] strArray2 = { "194261", "NL91RABO0315273637",
					"Clothes from Jan Bakker", "21.6", "-41.83", "-20.23" };
			String[] strArray3 = { "194262", "NL91RABO0315273730",
					"Clothes from Louis", "0", "2", "2" };

			csvdata.add(strArray1);
			csvdata.add(strArray2);
			csvdata.add(strArray3);
		} catch (Exception e) {
			e.getMessage();
		}
		return csvdata;
	}

	@Test
	public void withoutDuplicatedReferenceTest() throws CustStmtException {
		try {
			String[] strArray4 = { "194258", "TTDRABO0315273ffg",
					"Clothes from Louis", "0", "-41.83", "-41.83" };
			Map<String, String> actual = new HashMap<String, String>();
			Iterator<String[]> iterator = null;
			List<String[]> csvdata = csvValuesFactory();
			csvdata.add(strArray4);
			iterator = csvdata.iterator();
			actual = custFileVerifyServiceImpl.csvValidationLogic(iterator);
			assert (actual.isEmpty());
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@Test
	public void withDuplicatedReferenceTest() throws CustStmtException {
		try {
			String[] strArray4 = { "194262", "SSDRABO0315273ffg",
					"Clothes from Louis", "0", "-41.83", "-41.83" };
			Map<String, String> actual = new HashMap<String, String>();
			Iterator<String[]> iterator = null;
			List<String[]> csvdata = csvValuesFactory();
			csvdata.add(strArray4);
			iterator = csvdata.iterator();
			Map<String, String> expected = new HashMap<String, String>();
			expected.put(strArray4[0], strArray4[1]);
			actual = custFileVerifyServiceImpl.csvValidationLogic(iterator);
			assertEquals(expected, actual);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@Test
	public void endBalanceTest() throws CustStmtException {
		try {
			String[] strArray5 = { "194333", "YYYRABO0315273999",
					"Clothes from Louis", "90", "-41.83", "-1.83" };
			Map<String, String> actual = new HashMap<String, String>();
			Iterator<String[]> iterator = null;
			List<String[]> csvdata = csvValuesFactory();
			csvdata.add(strArray5);
			iterator = csvdata.iterator();
			Map<String, String> expected = new HashMap<String, String>();
			expected.put(strArray5[0], strArray5[1]);
			actual = custFileVerifyServiceImpl.csvValidationLogic(iterator);
			assertEquals(expected, actual);
		} catch (Exception e) {
			e.getMessage();
		}
	}

}