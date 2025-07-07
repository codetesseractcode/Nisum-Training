package com.nisum.greeter.service.impl;

import com.nisum.greeter.service.GreeterService;

/**
 * Basic implementation for Java Config approach
 * Following Single Responsibility Principle - only handles greeting logic
 */
public class BasicGreeterService implements GreeterService {

    @Override
    public String greet(String name) {
        return "Hello from Java Config, " + name + "!";
    }
}
