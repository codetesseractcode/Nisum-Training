package com.service.a.service_a.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private final ServiceProperties serviceProperties;

    public WebClientConfig(ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeader("Authorization", "Bearer " + serviceProperties.getJwt().getToken())
                .build();
    }
}
