package com.correvate.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import com.correvate.config.StorageConfig;
import com.correvate.exception.FileNotFoundException;
import com.correvate.exception.FileStorageException;
import com.correvate.service.impl.FileStorageServiceImpl;


public class FileStorageServiceTest {

	private StorageConfig properties = new StorageConfig();
	private FileStorageServiceImpl service;
	
	
	@BeforeEach
	public void init() {
		
		service = new FileStorageServiceImpl(properties);
		service.initialize();
	}
	@Test
	public void shouldSaveUploadedFilesAsZip() throws Exception {
		MockMultipartFile multipartFile1 = new MockMultipartFile("file", "test1.txt",
				"text/plain", "Test File 1".getBytes());
		MockMultipartFile multipartFile2 = new MockMultipartFile("file", "test2.txt",
						"text/plain", "Test File 2".getBytes());
		MockMultipartFile [] files = new MockMultipartFile [2];
		files[0]=multipartFile1;
		files[1]=multipartFile2;
		
		service.uploadFile(files);
		Path fileLocation = Paths.get(properties.getDownloadLocation()).resolve("multiCompressed.zip");
		File file = fileLocation.toFile();
		assertTrue(file.exists());
	}
	
	@Test
	public void shouldThrowExceptionIfFileNotFound() throws Exception {
		MockMultipartFile multipartFile1 = new MockMultipartFile("file", "test2.txt",
				"text/plain", "".getBytes());
		MockMultipartFile [] files = new MockMultipartFile [1];
		files[0]=multipartFile1;
		assertThrows(FileNotFoundException.class,()-> {
			service.uploadFile(files);
		});
		
		
	}
	
	@Test
	public void shouldDownloadZippedFile() throws Exception {
		Resource resource = service.downloadFile("multiCompressed.zip");
		assertTrue(resource.exists());
	}

}
