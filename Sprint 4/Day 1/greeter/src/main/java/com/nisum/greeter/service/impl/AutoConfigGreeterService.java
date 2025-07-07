package com.nisum.greeter.service.impl;

import com.nisum.greeter.service.GreeterService;

/**
 * Auto-configuration implementation for conditional autoconfiguration approach
 * Following Single Responsibility Principle
 */
public class AutoConfigGreeterService implements GreeterService {

    @Override
    public String greet(String name) {
        return "Hello from Auto Configuration, " + name + "!";
    }
}
