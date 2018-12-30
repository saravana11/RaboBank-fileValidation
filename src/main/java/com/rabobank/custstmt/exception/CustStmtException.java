package com.rabobank.custstmt.exception;

public class CustStmtException extends Exception {
	private static final long serialVersionUID = 1L;

	private String errorMessage;

	public String getErrorMessage() {

		return errorMessage;

	}

	public CustStmtException(String errorMessage) {

		super(errorMessage);

		this.errorMessage = errorMessage;

	}

	public CustStmtException() {

		super();

	}

}
