package com.joedago.studentsrecord.model;

import java.util.Map;

public class ErrorResponse {
	
	private String code;
	private String message;
	private Map<String, Map<String, String>> context;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, Map<String, String>> getContext() {
		return context;
	}
	public void setContext(Map<String, Map<String, String>> context) {
		this.context = context;
	}

}
