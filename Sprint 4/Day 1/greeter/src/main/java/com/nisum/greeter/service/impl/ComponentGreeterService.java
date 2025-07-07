package com.nisum.greeter.service.impl;

import com.nisum.greeter.service.GreeterService;
import org.springframework.stereotype.Component;

/**
 * Component-based implementation for @Component scan approach
 * Following Single Responsibility Principle
 */
@Component("componentGreeterService")
public class ComponentGreeterService implements GreeterService {

    @Override
    public String greet(String name) {
        return "Hello from Component Scan, " + name + "!";
    }
}
