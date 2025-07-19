package com.service.a.service_a.service;

import com.service.a.service_a.config.ServiceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ServiceBClient {

    private static final Logger logger = LoggerFactory.getLogger(ServiceBClient.class);

    private final WebClient webClient;
    private final ServiceProperties serviceProperties;

    public ServiceBClient(WebClient webClient, ServiceProperties serviceProperties) {
        this.webClient = webClient;
        this.serviceProperties = serviceProperties;
    }

    public Mono<String> getProtectedData() {
        logger.info("Calling Service B protected endpoint: {}", serviceProperties.getB().getUrl());

        return webClient.get()
                .uri(serviceProperties.getB().getUrl())
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> logger.info("Received response from Service B: {}", response))
                .doOnError(error -> logger.error("Error calling Service B: {}", error.getMessage()));
    }
}
