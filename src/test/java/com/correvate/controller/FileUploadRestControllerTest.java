package com.correvate.controller;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.correvate.service.FileStorageService;
@AutoConfigureMockMvc
@SpringBootTest
public class FileUploadRestControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private FileStorageService storageService;
	
	@Test
	public void notSettingFilesParamGives400BadRequest() throws Exception {	
		
		MockMultipartFile multipartFile1 = new MockMultipartFile("file", "test1.txt",
			"text/plain", "Test File 1".getBytes());
	MockMultipartFile multipartFile2 = new MockMultipartFile("file", "test2.txt",
					"text/plain", "Test File 2".getBytes());
	MockMultipartFile [] files = new MockMultipartFile [2];
	files[0]=multipartFile1;
	files[1]=multipartFile2;
		
		this.mvc.perform(multipart("/uploadMultiFile").file(multipartFile1)).andExpect(status().is(400));
	}
	
	@Test
	public void fileNotSentByClientRequest() throws Exception {	
		
		
		this.mvc.perform(multipart("/uploadMultiFile")).andExpect(status().is4xxClientError());
		//this.mvc.perform(multipart("/uploadMultiFile").file("files",files.toString().getBytes())).andExpect(status().is(400));
	}
	@Test
	public void testThrowsExceptionFileGreater5MB() throws Exception
	{
		//to be developed
	}
	
	@Test
	public void testZippedFileHasCorrectFilesAfterUpload() throws Exception
	{
		//to be developed
	}

}
