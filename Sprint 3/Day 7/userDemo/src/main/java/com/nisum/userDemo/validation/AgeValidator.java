package com.nisum.userDemo.validation;

/**
 * Interface for age validation following Interface Segregation Principle (ISP)
 * Clients should not be forced to depend on interfaces they don't use
 */
public interface AgeValidator {
    void validateAge(int age);
}
