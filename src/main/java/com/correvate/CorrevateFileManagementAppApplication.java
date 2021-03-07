package com.correvate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.correvate.config.StorageConfig;
import com.correvate.service.FileStorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageConfig.class)
public class CorrevateFileManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorrevateFileManagementAppApplication.class, args);
	}
	@Bean
	CommandLineRunner init(FileStorageService storageService) {
		return (args) -> {
			storageService.initialize();
		};
	}
}
