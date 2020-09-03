package com.joedago.studentsrecord.exception;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ExceptionResponse> handleResponseStatusException(ResponseStatusException ex) {
		final ExceptionResponse response = new ExceptionResponse();
		response.setHttpStatus(ex.getStatus().value());
		response.setCustomError(ex.getReason());
		response.setDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		return new ResponseEntity<>(response, ex.getStatus());
	}
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		final ExceptionResponse response = new ExceptionResponse();
		response.setHttpStatus(HttpStatus.BAD_REQUEST.value());
		response.setDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		response.setErrors(ex.getBindingResult().getAllErrors().stream()
				.map(error ->((FieldError) error).getField() + " - " +  error.getDefaultMessage())
				.collect(Collectors.toList()));
	    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
