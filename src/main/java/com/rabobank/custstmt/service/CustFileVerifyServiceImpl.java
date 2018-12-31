package com.rabobank.custstmt.service;
import com.rabobank.custstmt.exception.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.opencsv.CSVReader;
import com.rabobank.custstmt.constants.RaboCustConstants;
import com.rabobank.custstmt.controller.RestApiController;
import com.rabobank.custstmt.models.Records;
import com.rabobank.custstmt.models.UploadFileResponse;

@Service("CustFileVerifyService")
public class CustFileVerifyServiceImpl implements CustFileVerifyService {
	private CSVReader csvreader;
	public static final Logger logger = LoggerFactory
			.getLogger(RestApiController.class);

	/* (non-Javadoc)
	 * @see com.rabobank.custstmt.service.CustFileVerifyService#custFileVerify(org.springframework.web.multipart.MultipartFile, java.lang.String, java.io.InputStream)
	 */
	@Override
	public Map<String, String> custFileVerify(MultipartFile file,
			String contentType, InputStream inputStream) throws CustStmtException {
		logger.info("contentType::::::" + contentType);
		Map<String, String> map = new HashMap<String, String>();
		if (RaboCustConstants.xml.equalsIgnoreCase(contentType)) {
			map = XMLLogic(file);
		} else if (RaboCustConstants.csv.equalsIgnoreCase(contentType)) {
			map = CSVLogic(inputStream);
		}else{
			 throw new CustStmtException(RaboCustConstants.invalidFile);
		}
		return map;
	}
/**
 * @param record
 * @param fieldname
 * @param rec
 * @throws CustStmtException
 */
private void validatenonNumericInput(String record,String fieldname,int rec) throws CustStmtException{
	if (StringUtils.isBlank(record)) {
		//logger.info("nonNumericInput record::::::" + record);
		           throw new  CustStmtException("Invalid "+fieldname+" for record no "+rec);
		        }
}


	/**
	 * @param inputStream
	 * @return
	 * @throws CustStmtException
	 */
	private Map<String, String> CSVLogic(InputStream inputStream) throws CustStmtException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Reader reader = new InputStreamReader(inputStream,
					StandardCharsets.UTF_8);
			csvreader = new CSVReader(reader);
			String[] record = null;
			int rec_cnt = 0;
			Set<String> addedReferencenos = new HashSet<>();
			while ((record = csvreader.readNext()) != null) {
				if (rec_cnt > 0) {
					validatenonNumericInput(record[1],RaboCustConstants.accno,rec_cnt);
					validatenonNumericInput(record[2],RaboCustConstants.description,rec_cnt);
					validatenonNumericInput(record[0],RaboCustConstants.reference,rec_cnt);
					validatenonNumericInput(record[3],RaboCustConstants.startBalance,rec_cnt);
					validatenonNumericInput(record[4],RaboCustConstants.mutation,rec_cnt);
					validatenonNumericInput(record[5],RaboCustConstants.endbal,rec_cnt);
					boolean refValid = addedReferencenos.add(record[0]);
					if (!refValid
							|| !endBalValid(record[0], record[3], record[4],
									record[5])) {
						map.put(record[0], record[1]);
					}
				}
				rec_cnt++;
			}

			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return map;
	}

	/**
	 * @param ref
	 * @param strt
	 * @param mut
	 * @param end
	 * @return
	 */
	private boolean endBalValid(String ref, String strt, String mut, String end) {
		boolean isendBalret;
		BigDecimal startBalvalue = BigDecimal.valueOf(Double.valueOf(strt));
		BigDecimal mutValue = BigDecimal.valueOf(Double.valueOf(mut));
		BigDecimal endBalValue = BigDecimal.valueOf(Double.valueOf(end));
		/*logger.info("startBalance::" + startBalvalue + ":::::::::"
				+ "mutation::" + mutValue + ":::::" + "endBalance::"
				+ endBalValue);*/
		BigDecimal expectedEndBalance = startBalvalue.add(mutValue);
		isendBalret = expectedEndBalance.compareTo(endBalValue) == 0;
		return isendBalret;
	}

	/**
	 * @param file
	 * @return
	 * @throws CustStmtException
	 */
	private Map<String, String> XMLLogic(MultipartFile file) throws CustStmtException {
		ObjectMapper objectMapper = new XmlMapper();
		Records records = null;
		Map<String, String> map = new HashMap<String, String>();

		try {
			records = objectMapper.readValue(StringUtils.toEncodedString(
					file.getBytes(), StandardCharsets.UTF_8), Records.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//logger.info("records::::::" + records);
		Set<String> addedReferencenos = new HashSet<>();
		int rec_cnt=1;
		for (UploadFileResponse r : records.getRecord()) {
			//logger.info("r value::::::" + r.getAccountNumber());
			//logger.info("r value::::::" + r.getStartBalance());
			validatenonNumericInput(r.getAccountNumber(),RaboCustConstants.accno,rec_cnt);
			validatenonNumericInput(r.getDescription(),RaboCustConstants.description,rec_cnt);
			validatenonNumericInput(r.getReference(),RaboCustConstants.reference,rec_cnt);
			validatenonNumericInput(r.getStartBalance(),RaboCustConstants.startBalance,rec_cnt);
			validatenonNumericInput(r.getMutation(),RaboCustConstants.mutation,rec_cnt);
			validatenonNumericInput(r.getEndBalance(),RaboCustConstants.endbal,rec_cnt);
			boolean refValid = addedReferencenos.add(r.getReference());
			if (!refValid
					|| !endBalValid(r.getReference(), r.getStartBalance(),
							r.getMutation(), r.getEndBalance())) {
				map.put(r.getReference(), r.getDescription());
			}

		}
		return map;
	}

}
