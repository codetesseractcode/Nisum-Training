package com.nisum.greeter.runner;

import com.nisum.greeter.service.GreeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Command line runner to demonstrate the different bean definition approaches
 * Following Dependency Inversion Principle - depends on GreeterService abstraction
 */
@Component
public class GreeterRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(GreeterRunner.class);

    private final ApplicationContext applicationContext;

    public GreeterRunner(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) {
        logger.info("=== Greeter Service Bean Analysis ===");

        // Get all GreeterService beans
        Map<String, GreeterService> greeterBeans = applicationContext.getBeansOfType(GreeterService.class);

        logger.info("Found {} GreeterService bean(s):", greeterBeans.size());

        for (Map.Entry<String, GreeterService> entry : greeterBeans.entrySet()) {
            String beanName = entry.getKey();
            GreeterService service = entry.getValue();
            String className = service.getClass().getSimpleName();

            logger.info("Bean name: {} | Class: {}", beanName, className);
            logger.info("Greeting: {}", service.greet("Spring Boot"));
        }

        logger.info("=== End Analysis ===");
    }
}
