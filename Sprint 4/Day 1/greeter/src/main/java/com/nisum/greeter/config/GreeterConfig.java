package com.nisum.greeter.config;

import com.nisum.greeter.service.GreeterService;
import com.nisum.greeter.service.impl.BasicGreeterService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Java Configuration class demonstrating @Bean definition approach
 * Following Dependency Inversion Principle - depends on abstractions
 */
@Configuration
public class GreeterConfig {

    /**
     * Bean definition using Java Config
     * @ConditionalOnMissingBean ensures this bean is only created if no other GreeterService exists
     */
    @Bean
    @ConditionalOnMissingBean(GreeterService.class)
    public GreeterService basicGreeterService() {
        return new BasicGreeterService();
    }
}
