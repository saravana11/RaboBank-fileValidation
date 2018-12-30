package com.rabobank.custstmt.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.Arrays;
@JacksonXmlRootElement(localName = "records") 
	public final class Records {
    @JacksonXmlElementWrapper(localName = "record", useWrapping = false)
    private UploadFileResponse[] record;
    public Records() {
    }
    public Records(UploadFileResponse[] record) {
        this.record = record;
    }
    
    
    public UploadFileResponse[] getRecord() {
		return record;
	}
	public void setRecord(UploadFileResponse[] record) {
		this.record = record;
	}
	@Override public String toString() {
        return "Employees{" +
                "employees=" + Arrays.toString(record) +
                '}';
    }
}
