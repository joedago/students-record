package com.joedago.studentsrecord.exception;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExceptionResponse {
	
	@JsonProperty("http-status")
	private Integer httpStatus;
	@JsonProperty("custom-error")
	private String customError;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp date;
	private List<String> errors;
	
	public Integer getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getCustomError() {
		return customError;
	}
	public void setCustomError(String customError) {
		this.customError = customError;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
