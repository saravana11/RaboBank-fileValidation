package com.rabobank.custstmt.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class UploadFileResponse {
    @JacksonXmlProperty(localName = "reference", isAttribute = true)
	private String Reference;
    @JacksonXmlProperty(localName = "accountNumber")
    private String accountNumber;
    @JacksonXmlProperty(localName = "description")
    private String description;	
    @JacksonXmlProperty(localName = "startBalance")
    private String startBalance;
    @JacksonXmlProperty(localName = "mutation")
    private String mutation;	
    @JacksonXmlProperty(localName = "endBalance")
    private String endBalance;
    
	@Override
	public String toString() {
		return "UploadFileResponse [Reference=" + Reference
				+ ", accountNumber=" + accountNumber + ", description="
				+ description + ", startBalance=" + startBalance
				+ ", mutation=" + mutation + ", endBalance=" + endBalance + "]";
	}
	public UploadFileResponse(String reference, String accountNumber,
			String description, String startBalance, String mutation,
			String endBalance) {
		super();
		Reference = reference;
		this.accountNumber = accountNumber;
		this.description = description;
		this.startBalance = startBalance;
		this.mutation = mutation;
		this.endBalance = endBalance;
	}
	public UploadFileResponse() {
		// TODO Auto-generated constructor stub
	}
	public String getReference() {
		return Reference;
	}
	public void setReference(String reference) {
		Reference = reference;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartBalance() {
		return startBalance;
	}
	public void setStartBalance(String startBalance) {
		this.startBalance = startBalance;
	}
	public String getMutation() {
		return mutation;
	}
	public void setMutation(String mutation) {
		this.mutation = mutation;
	}
	public String getEndBalance() {
		return endBalance;
	}
	public void setEndBalance(String endBalance) {
		this.endBalance = endBalance;
	}
		   
}