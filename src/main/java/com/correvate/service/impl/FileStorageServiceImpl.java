package com.correvate.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.correvate.config.StorageConfig;
import com.correvate.exception.FileNotFoundException;
import com.correvate.exception.FileStorageException;
import com.correvate.service.FileStorageService;
import com.correvate.utils.FileUploadUtils;
@Service
public class FileStorageServiceImpl implements FileStorageService {
	private static Logger logger = LoggerFactory.getLogger(FileStorageServiceImpl.class);
	private final Path filePath;
	private final Path zippedFilePath;
	
	@Autowired
	public FileStorageServiceImpl(StorageConfig properties) {
		this.filePath= Paths.get(properties.getLocation());
		this.zippedFilePath=Paths.get(properties.getDownloadLocation());
	}
	/**
	 * Intialize on service start which creates directories to save files and zipped files
	 */
	@Override
	public void initialize() {
		try {
			logger.debug("Creating file directories");
			Files.createDirectories(filePath);
			Files.createDirectories(zippedFilePath);
		}
		catch (IOException e) {
			logger.debug("Error Creating file directories");
			throw new FileStorageException("Could not initialize storage", e);
			
		}
		
	}
	/**
	 * The method uploads a list of files to the server. If files do not exists it throws an exception
	 */
	@Override
	public void uploadFile(MultipartFile[] files) {
		
		try {
			logger.debug("Inside FileStorageServiceImpl.uploadFile()");
			for(MultipartFile file:files)
			{
				
					if (file.isEmpty()) {
						logger.error("File doesnot exists or the files are not selected");
						throw new FileNotFoundException("File doesnot exists or the files are not selected");
					}
					Path destinationFile = this.filePath.resolve(
							Paths.get(file.getOriginalFilename()))
							.normalize().toAbsolutePath();
					if(!validateDestinationPath(destinationFile))
					{
						logger.error("The file needs to be stored in correct directory");
						throw new FileStorageException(
								"The file needs to be stored in correct directory");
					}
					try (InputStream inputStream = file.getInputStream()) {
						Files.copy(inputStream, destinationFile,
							StandardCopyOption.REPLACE_EXISTING);
						logger.info("File Uploaded succedded");
					}
				
			}
			
			createZippedFile();
		} catch (IOException e) {
			logger.error("The file upload failed");
			throw new FileStorageException(
					"The file upload failed");
		}
		
		
		
	}

	/**
	 * The method creates a zippedfile from the list of files in the directory.
	 * To Do: This code needs to be changed to have seperate folders for each request if multiple users are using 
	 */
	public void createZippedFile() throws IOException {
		logger.debug("Inside FileStorageServiceImpl.createZippedFile()");
		List<Path> result;
        try (Stream<Path> walk = Files.walk(this.filePath, 1)) {
            result = walk
                    .filter(Files::isRegularFile)   // is a file
                    .collect(Collectors.toList());
        }
        FileUploadUtils.zipFiles(result,this.zippedFilePath);//zips the file in finds inside the directory
        FileSystemUtils.deleteRecursively(this.filePath.toFile());
        Files.createDirectories(this.filePath);
		
	}

	
	/**
	 * The method downloads the file if the file with the filename exists
	 */
	@Override
	public Resource downloadFile(String filename) {
		logger.debug("Inside FileStorageServiceImpl.downloadFile()");
		try {
			Path file = zippedFilePath.resolve(filename);
			
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new FileStorageException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new FileNotFoundException("Could not read file: " + filename, e);
		}
	}
	/**
	 * The method validates if the file is stored in correct directory
	 */
	public boolean validateDestinationPath(Path destinationFile)
	{
		return destinationFile.getParent().equals(this.filePath.toAbsolutePath());
	}

}
