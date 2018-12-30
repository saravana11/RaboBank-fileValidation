package com.rabobank.custstmt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.rabobank.custstmt.models.ErrorResponse;

@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {

		ErrorResponse error = new ErrorResponse();

		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		error.setMessage("Please contact your administrator");

		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);

	}
	
	

}
