package com.nisum.greeter.service;

/**
 * Simple greeter service interface following SOLID principles (Interface Segregation)
 */
public interface GreeterService {
    String greet(String name);
}
