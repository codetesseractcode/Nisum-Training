package com.nisum.library.library_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * Basic application context test to ensure the application starts successfully.
 */
@SpringBootTest
@Import(TestcontainersConfiguration.class)
class LibraryApiApplicationTests {

    @Test
    void contextLoads() {
        // This test ensures that the Spring application context loads successfully
        // with all the configured beans and components
    }
}
