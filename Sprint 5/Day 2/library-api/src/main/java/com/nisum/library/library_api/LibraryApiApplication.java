package com.nisum.library.library_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main Spring Boot application class for Library Management API.
 * Implements secure, scalable library management with comprehensive features.
 *
 * Features included:
 * - JWT-based authentication and authorization
 * - Role-based access control (ADMIN, LIBRARIAN, USER)
 * - API rate limiting and CORS protection
 * - Comprehensive input validation and XSS prevention
 * - SQL injection prevention with parameterized queries
 * - Secure password hashing with BCrypt
 * - HTTPS enforcement and security headers
 * - Comprehensive testing with TestContainers
 * - Code quality tools integration (JaCoCo, Checkstyle, PMD, SpotBugs)
 * - OpenAPI 3.0 documentation
 * - Caching for improved performance
 *
 * @author Nisum Library Team
 * @version 1.0
 * @since 2025-07-19
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@EnableCaching
public class LibraryApiApplication {

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(LibraryApiApplication.class, args);
    }
}
