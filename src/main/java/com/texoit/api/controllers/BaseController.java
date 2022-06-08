package com.texoit.api.controllers;

import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.texoit.api.exceptions.BadRequestException;
import com.texoit.api.exceptions.ErrorResponse;
import com.texoit.api.exceptions.ServiceException;

@CrossOrigin(origins = "*")
@RestController
public abstract class BaseController {

	private static final Logger LOGGER = LogManager.getLogger(BaseController.class);

	private ResponseEntity<ErrorResponse> processException(String message, HttpStatus status) {
		ErrorResponse error = new ErrorResponse();
		error.setStatus(status);
		error.setErrorCode(status.value());
		error.setMessage(message);
		error.setTime(new Timestamp(System.currentTimeMillis()));
		return new ResponseEntity<>(error, status);
	}

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ErrorResponse> processException(ServiceException ex) {
		return processException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> processException(BadRequestException ex) {
		return processException(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> processException(AccessDeniedException ex) {
		return processException(ex.getMessage(), HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ErrorResponse> processException(ResponseStatusException ex) {
		return processException(ex.getMessage(), HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> processException(Exception ex) {
		LOGGER.error("There was an error to process the operation...", ex);
		return processException("There was an error to process the operation...",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

}
