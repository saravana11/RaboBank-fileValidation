package com.rabobank.custstmt.models;

import java.util.Map;

public class InvalidData {
private Map<String,String> failedRecords;



public Map<String, String> getFailedRecords() {
	return failedRecords;
}



public void setFailedRecords(Map<String, String> failedRecords) {
	this.failedRecords = failedRecords;
}



public InvalidData(Map<String, String> failedRecords) {
	super();
	this.failedRecords = failedRecords;
}

}
