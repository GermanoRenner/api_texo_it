package com.texoit.api.exceptions;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
	
	private Integer errorCode;
	private String message;
	private HttpStatus status;
	private Timestamp time;
	
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
}
