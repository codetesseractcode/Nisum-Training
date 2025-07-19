package com.nisum.library.library_api;

import org.springframework.boot.SpringApplication;

/**
 * Test application class for running tests with TestContainers.
 * Provides isolated test environment for integration testing.
 */
public class TestLibraryApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(LibraryApiApplication::main)
                .with(TestcontainersConfiguration.class)
                .run(args);
    }
}
