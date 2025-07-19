package com.service.a.service_a.controller;

import com.service.a.service_a.service.ServiceBClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class GatewayController {

    private final ServiceBClient serviceBClient;

    public GatewayController(ServiceBClient serviceBClient) {
        this.serviceBClient = serviceBClient;
    }

    @GetMapping("/call-service-b")
    public Mono<ResponseEntity<String>> callServiceB() {
        return serviceBClient.getProtectedData()
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }
}
