package com.rabobank.custstmt.service;

import java.io.InputStream;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.rabobank.custstmt.exception.CustStmtException;

public interface CustFileVerifyService {
	Map<String, String> custFileVerify(MultipartFile file, String contentType,
			InputStream inputStream)throws CustStmtException; 
}
