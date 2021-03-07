package com.correvate.exception;
/**
 * The exception is thrown if file is not found when we fetch for it
 */
public class FileNotFoundException extends RuntimeException{
	public FileNotFoundException(String message) {
		super(message);
	}

	public FileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}

