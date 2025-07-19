package com.service.a.service_a;

import com.service.a.service_a.service.ServiceBClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties
public class ServiceAApplication {

	private static final Logger logger = LoggerFactory.getLogger(ServiceAApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServiceAApplication.class, args);
	}

	@Bean
	public CommandLineRunner callServiceB(ServiceBClient serviceBClient) {
		return args -> {
			logger.info("Starting Service A - calling Service B protected endpoint...");
			serviceBClient.getProtectedData()
					.subscribe(
							response -> logger.info("Successfully received response from Service B: {}", response),
							error -> logger.error("Failed to call Service B: {}", error.getMessage())
					);
		};
	}
}
