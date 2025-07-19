package com.nisum.library.library_api.exception;

/**
 * Exception thrown when attempting to create a user that already exists.
 * Used for preventing duplicate user registration.
 *
 * @author Nisum Library Team
 * @version 1.0
 * @since 2025-07-19
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new UserAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserAlreadyExistsException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
