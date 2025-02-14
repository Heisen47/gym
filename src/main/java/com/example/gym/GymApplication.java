package com.example.gym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class GymApplication {
	private static final Logger logger = LogManager.getLogger(GymApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GymApplication.class, args);
		logger.info("Application started successfully.");
	}

}
