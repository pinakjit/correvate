package com.correvate.exception;
/**
 * Throws the problem if there is any problem while storing the file
 */
public class FileStorageException extends RuntimeException {
	public FileStorageException(String message) {
		super(message);
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
