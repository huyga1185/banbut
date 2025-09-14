package com.web.banbut;

import com.web.banbut.service.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BanbutApplication implements CommandLineRunner {

	private final FileStorageService fileStorageService;

	public BanbutApplication(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BanbutApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		fileStorageService.resetStorage();//This method makes the storage reset. should be deleted when we want to hold back the data in storage when re-run.
		fileStorageService.init();//This method creates a new directory for upload
	}
}
