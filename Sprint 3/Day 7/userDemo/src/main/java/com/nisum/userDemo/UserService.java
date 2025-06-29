package com.nisum.userDemo;

import com.nisum.userDemo.validation.AgeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserService following SOLID principles:
 * - Single Responsibility: Only handles user-related business logic
 * - Open/Closed: Open for extension (new validators), closed for modification
 * - Dependency Inversion: Depends on AgeValidator abstraction, not concrete implementation
 */
@Service
public class UserService {

    private final AgeValidator ageValidator;

    @Autowired
    public UserService(AgeValidator ageValidator) {
        this.ageValidator = ageValidator;
    }

    public void validateAge(int age) {
        ageValidator.validateAge(age);
    }
}
