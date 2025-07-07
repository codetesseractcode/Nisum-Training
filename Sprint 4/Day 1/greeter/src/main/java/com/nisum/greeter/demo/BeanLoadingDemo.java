package com.nisum.greeter.demo;

import com.nisum.greeter.service.GreeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Demo class to show different bean loading scenarios
 * This demonstrates what happens when we disable component scanning
 */
@Component
public class BeanLoadingDemo implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(BeanLoadingDemo.class);

    private final ApplicationContext context;

    public BeanLoadingDemo(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(String... args) {
        if (args.length > 0 && "demo".equals(args[0])) {
            logger.info("=== Bean Loading Order Demonstration ===");

            // Show which bean implementation is actually loaded
            GreeterService greeter = context.getBean(GreeterService.class);
            logger.info("Active implementation: {}", greeter.getClass().getSimpleName());
            logger.info("Message: {}", greeter.greet("Developer"));

            // Show all GreeterService beans in context
            String[] beanNames = context.getBeanNamesForType(GreeterService.class);
            logger.info("Total GreeterService beans found: {}", beanNames.length);
            for (String beanName : beanNames) {
                Object bean = context.getBean(beanName);
                logger.info("- {} ({})", beanName, bean.getClass().getSimpleName());
            }

            logger.info("=== End Demo ===");
        }
    }
}
