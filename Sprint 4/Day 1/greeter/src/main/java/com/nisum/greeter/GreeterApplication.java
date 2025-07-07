package com.nisum.greeter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GreeterApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreeterApplication.class, args);
	}

	// Private constructor to hide the implicit public one (SonarQube rule)
	private GreeterApplication() {
		// Utility class should not be instantiated
	}

}
