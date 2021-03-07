package com.correvate.exception;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * The class maps all exceptions and responds back with a generic response with appropiate status messages
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		GenericExceptionResponse GenericExceptionResponse = new GenericExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity(GenericExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	};

	@ExceptionHandler(FileNotFoundException.class)
	public final ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException ex, WebRequest request) {
		GenericExceptionResponse GenericExceptionResponse = new GenericExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity(GenericExceptionResponse, HttpStatus.NOT_FOUND);
	};
	
	@ExceptionHandler(FileStorageException.class)
	public final ResponseEntity<Object> handleFileStorageException(FileStorageException ex, WebRequest request) {
		GenericExceptionResponse GenericExceptionResponse = new GenericExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity(GenericExceptionResponse, HttpStatus.NOT_FOUND);
	};

}