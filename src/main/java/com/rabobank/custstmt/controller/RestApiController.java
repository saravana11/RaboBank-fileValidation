package com.rabobank.custstmt.controller;

import java.io.InputStream;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rabobank.custstmt.exception.CustStmtException;
import com.rabobank.custstmt.models.ErrorResponse;
import com.rabobank.custstmt.models.InvalidData;
import com.rabobank.custstmt.service.CustFileVerifyService;

/** this controller is to validate the input file **/
@RestController
@RequestMapping("/raboapi")
public class RestApiController {

	public static final Logger logger = LoggerFactory
			.getLogger(RestApiController.class);

	@Autowired
	CustFileVerifyService custFileVerifyService;

	/**
	 * It returns the correct subtype of the given MIME type.
	 * 
	 * @param mimeType
	 *            the mime type as a string
	 * @return the subtype Windows property by default sets the content type as
	 *         vnd.ms-excel. But the standard MIME type for CSV is text/csv as
	 *         per the standards
	 */

	/**
	 * @param mimeType
	 * @return
	 */
	public static String getSubTypeOf(String mimeType) {
		int index = mimeType.indexOf('/');
		String subType = mimeType.substring(index + 1);
		if (StringUtils.contains(subType, "EXCEL")) {
			return "CSV";
		} else {
			return subType;
		}
	}

	/**
	 * this controller method is to validate the input file by invoking the
	 * custfileverify() service method
	 **/

	/**
	 * @param file
	 * @return
	 * @throws CustStmtException
	 */
	@RequestMapping(value = "/custfileverify", method = RequestMethod.POST)
	public InvalidData custFileVerify(@RequestPart MultipartFile file)
			throws CustStmtException{
		Map<String, String> invalidData = null;
		try (InputStream inputStream = file.getInputStream()) {
			String contentType = getSubTypeOf(file.getContentType());
			invalidData = custFileVerifyService.custFileVerify(file,
					contentType, inputStream);
		}catch (CustStmtException exception) {
			//exception.printStackTrace();
			throw new CustStmtException(exception.getMessage());
			// exception.printStackTrace();
		}
		catch (Exception exception) {
			exception.printStackTrace();
			throw new CustStmtException(exception.getMessage());
			// exception.printStackTrace();
		}
		return new InvalidData(invalidData);

	}

	/** this is where the exception handler logic is carried out **/
	@ExceptionHandler(CustStmtException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(CustStmtException ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);

	}

}