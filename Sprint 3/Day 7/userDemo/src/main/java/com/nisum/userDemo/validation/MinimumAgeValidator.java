package com.nisum.userDemo.validation;

import org.springframework.stereotype.Component;

/**
 * Concrete implementation of AgeValidator following Single Responsibility Principle (SRP)
 * This class has only one responsibility: validating age
 */
@Component
public class MinimumAgeValidator implements AgeValidator {

    private static final int MINIMUM_AGE = 18;
    private static final String UNDERAGE_MESSAGE = "Underage";

    @Override
    public void validateAge(int age) {
        if (age < MINIMUM_AGE) {
            throw new IllegalArgumentException(UNDERAGE_MESSAGE);
        }
    }
}
