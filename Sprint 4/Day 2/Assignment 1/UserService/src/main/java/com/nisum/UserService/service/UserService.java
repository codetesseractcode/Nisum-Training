package com.nisum.UserService.service;

import com.nisum.UserService.model.User;
import org.springframework.stereotype.Service;

/**
 * Service class for User operations
 * Following Single Responsibility Principle - handles user business logic
 */
@Service
public class UserService {

    /**
     * Retrieves a hardcoded user profile by ID
     * Following YAGNI principle - only implementing what's needed
     *
     * @param id the user ID (currently ignored as we return hardcoded data)
     * @return User object with hardcoded data
     */
    public User getUserById(Long id) {
        // Return hardcoded user data as per requirements
        return new User(1L, "Ayusman Pradhan", "pradhanayusman22k@gmail.com");
    }
}
