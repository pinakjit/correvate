package com.correvate.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.correvate.service.FileStorageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * The public controller to upload files to the server
 */
@RestController @Api(value="fileupload")
public class FileUploadRestController {
	  Logger logger = LoggerFactory.getLogger(FileUploadRestController.class);
	private final FileStorageService fileService;
	
	@Autowired
	public FileUploadRestController(FileStorageService fileService) {
		this.fileService = fileService;
	}
	/**
	 * The method uploads multiple files into the server and returns back with a zipped file with all the contents
	 */
	@ApiOperation(value="uploads a list of files and responds with a zip")
	@RequestMapping(value = "/uploadMultiFile", method = RequestMethod.POST,produces="application/zip")
	public ResponseEntity<Resource> submit(@RequestParam("files") MultipartFile[] files) {
		logger.debug("Inside FileUploadRestController.submit()");
		fileService.uploadFile(files);
		logger.debug("Files successfully uploaded to the server");
		Resource file = fileService.downloadFile("multiCompressed.zip");
		logger.debug("File to be downloaded created the file name is "+file.getFilename());
	    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	

}
