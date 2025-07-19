package com.nisum.library.library_api.exception;

/**
 * Exception thrown when a requested resource is not found.
 * Used for RESTful API error handling.
 *
 * @author Nisum Library Team
 * @version 1.0
 * @since 2025-07-19
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
