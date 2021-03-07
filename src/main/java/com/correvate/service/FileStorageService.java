package com.correvate.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
	public void initialize();
	public void uploadFile(MultipartFile[] files);
	
	public Resource downloadFile(String filename);
	
	
	
}
