package com.nisum.library.library_api.service;

import com.nisum.library.library_api.dto.SignupRequest;
import com.nisum.library.library_api.model.User;
import com.nisum.library.library_api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service class for user management operations.
 * Handles user registration, authentication, and profile management.
 *
 * @author Nisum Library Team
 * @version 1.0
 * @since 2025-07-19
 */
@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user in the system.
     */
    public User registerUser(SignupRequest signUpRequest) {
        logger.info("Registering new user: {}", signUpRequest.getUsername());

        // Check if username already exists
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken: " + signUpRequest.getUsername());
        }

        // Check if email already exists
        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use: " + signUpRequest.getEmail());
        }

        // Create new user
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setRole(User.Role.USER); // Default role
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        logger.info("Successfully registered user: {}", savedUser.getUsername());

        return savedUser;
    }

    /**
     * Finds a user by username or email.
     */
    @Transactional(readOnly = true)
    public User findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + usernameOrEmail));
    }

    /**
     * Finds a user by ID.
     */
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    /**
     * Gets all users with pagination.
     */
    @Transactional(readOnly = true)
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /**
     * Updates user profile information.
     */
    public User updateUserProfile(Long userId, SignupRequest updateRequest) {
        User existingUser = findById(userId);

        // Check if username is being changed and if new username already exists
        if (!existingUser.getUsername().equals(updateRequest.getUsername()) &&
            userRepository.findByUsername(updateRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken: " + updateRequest.getUsername());
        }

        // Check if email is being changed and if new email already exists
        if (!existingUser.getEmail().equals(updateRequest.getEmail()) &&
            userRepository.findByEmail(updateRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use: " + updateRequest.getEmail());
        }

        // Update user information
        existingUser.setUsername(updateRequest.getUsername());
        existingUser.setEmail(updateRequest.getEmail());
        existingUser.setFirstName(updateRequest.getFirstName());
        existingUser.setLastName(updateRequest.getLastName());
        existingUser.setUpdatedAt(LocalDateTime.now());

        // Update password if provided
        if (updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }

        User updatedUser = userRepository.save(existingUser);
        logger.info("Updated user profile: {}", updatedUser.getUsername());

        return updatedUser;
    }
}
