package com.nisum.library.library_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI 3.0 configuration for API documentation.
 * Provides comprehensive API documentation with security scheme definitions.
 *
 * @author Nisum Library Team
 * @version 1.0
 * @since 2025-07-19
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Library Management API",
        version = "1.0.0",
        description = "Secure REST API for Library Management System with comprehensive security features",
        contact = @Contact(
            name = "Nisum Library Team",
            email = "library-support@nisum.com",
            url = "https://nisum.com"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8089", description = "Development Server")
    }
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class OpenApiConfig {
}
