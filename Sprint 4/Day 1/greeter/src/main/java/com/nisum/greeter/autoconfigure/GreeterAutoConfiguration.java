package com.nisum.greeter.autoconfigure;

import com.nisum.greeter.service.GreeterService;
import com.nisum.greeter.service.impl.AutoConfigGreeterService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * Auto-configuration class for GreeterService
 * Following Open/Closed Principle - open for extension via configuration
 */
@AutoConfiguration
public class GreeterAutoConfiguration {

    /**
     * Conditional autoconfiguration bean
     * Only creates this bean if:
     * 1. No other GreeterService bean exists
     * 2. Property greeter.autoconfigure.enabled is true (defaults to true)
     */
    @Bean
    @ConditionalOnMissingBean(GreeterService.class)
    @ConditionalOnProperty(name = "greeter.autoconfigure.enabled", havingValue = "true", matchIfMissing = true)
    public GreeterService autoConfigGreeterService() {
        return new AutoConfigGreeterService();
    }
}
