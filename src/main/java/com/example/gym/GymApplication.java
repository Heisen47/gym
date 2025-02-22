package com.example.gym;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TimeZone;

@SpringBootApplication
public class GymApplication {
//	private static final Logger logger = LogManager.getLogger(GymApplication.class);

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
	}

	public static void main(String[] args) {
		SpringApplication.run(GymApplication.class, args);
//		logger.info("Application started successfully.");
	}

}
