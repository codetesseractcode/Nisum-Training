package com.nisum.library.library_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.library.library_api.dto.BookRequest;
import com.nisum.library.library_api.dto.LoginRequest;
import com.nisum.library.library_api.dto.SignupRequest;
import com.nisum.library.library_api.model.Book;
import com.nisum.library.library_api.model.User;
import com.nisum.library.library_api.repository.BookRepository;
import com.nisum.library.library_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests using @SpringBootTest and TestContainers.
 * Tests the complete application stack with real database.
 *
 * @author Nisum Library Team
 * @version 1.0
 * @since 2025-07-19
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "app.jwt.secret=testSecretKeyForIntegrationTestsOnly123456",
    "app.jwt.expiration=86400000"
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LibraryApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String baseUrl;
    private String jwtToken;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/v1";
        setupTestUser();
    }

    private void setupTestUser() {
        User testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword(passwordEncoder.encode("password123"));
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setRole(User.Role.ADMIN);
        userRepository.save(testUser);

        // Login to get JWT token
        LoginRequest loginRequest = new LoginRequest("testuser", "password123");
        ResponseEntity<String> loginResponse = restTemplate.postForEntity(
            baseUrl + "/auth/signin", loginRequest, String.class);

        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            try {
                var jsonNode = objectMapper.readTree(loginResponse.getBody());
                jwtToken = jsonNode.get("accessToken").asText();
            } catch (Exception e) {
                throw new RuntimeException("Failed to extract JWT token", e);
            }
        }
    }

    private HttpHeaders createAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (jwtToken != null) {
            headers.setBearerAuth(jwtToken);
        }
        return headers;
    }

    @Test
    void userRegistration_ShouldSucceed() {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("newuser");
        signupRequest.setEmail("newuser@example.com");
        signupRequest.setPassword("password123");
        signupRequest.setFirstName("New");
        signupRequest.setLastName("User");

        // When
        ResponseEntity<String> response = restTemplate.postForEntity(
            baseUrl + "/auth/signup", signupRequest, String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(userRepository.findByUsernameIgnoreCase("newuser")).isPresent();
    }

    @Test
    void userLogin_WithValidCredentials_ShouldReturnJwtToken() {
        // Given
        LoginRequest loginRequest = new LoginRequest("testuser", "password123");

        // When
        ResponseEntity<String> response = restTemplate.postForEntity(
            baseUrl + "/auth/signin", loginRequest, String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("accessToken");
        assertThat(response.getBody()).contains("testuser");
    }

    @Test
    void createBook_WithValidData_ShouldSucceed() {
        // Given
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("Integration Test Book");
        bookRequest.setAuthor("Test Author");
        bookRequest.setIsbn("978-0-123456-78-9");
        bookRequest.setCategory("Technology");
        bookRequest.setPublisher("Test Publisher");
        bookRequest.setPublicationDate(LocalDate.of(2023, 1, 1));
        bookRequest.setPageCount(300);
        bookRequest.setPrice(new BigDecimal("29.99"));
        bookRequest.setTotalCopies(10);

        HttpEntity<BookRequest> request = new HttpEntity<>(bookRequest, createAuthHeaders());

        // When
        ResponseEntity<String> response = restTemplate.exchange(
            baseUrl + "/books", HttpMethod.POST, request, String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(bookRepository.findByIsbn("978-0-123456-78-9")).isPresent();
    }

    @Test
    void getAllBooks_ShouldReturnPaginatedResults() {
        // Given - Create test books
        createTestBooks();

        HttpEntity<?> request = new HttpEntity<>(createAuthHeaders());

        // When
        ResponseEntity<String> response = restTemplate.exchange(
            baseUrl + "/books?page=0&size=5", HttpMethod.GET, request, String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("content");
        assertThat(response.getBody()).contains("totalElements");
    }

    @Test
    void borrowBook_WithAvailableCopies_ShouldSucceed() {
        // Given
        Book book = createTestBook();
        book.setAvailableCopies(5);
        book = bookRepository.save(book);

        HttpEntity<?> request = new HttpEntity<>(createAuthHeaders());

        // When
        ResponseEntity<String> response = restTemplate.exchange(
            baseUrl + "/books/" + book.getId() + "/borrow",
            HttpMethod.POST, request, String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Book borrowed successfully");

        Book updatedBook = bookRepository.findById(book.getId()).orElseThrow();
        assertThat(updatedBook.getAvailableCopies()).isEqualTo(4);
    }

    @Test
    void searchBooks_ShouldReturnMatchingResults() {
        // Given
        createTestBooks();

        HttpEntity<?> request = new HttpEntity<>(createAuthHeaders());

        // When
        ResponseEntity<String> response = restTemplate.exchange(
            baseUrl + "/books/search?query=Spring&page=0&size=10",
            HttpMethod.GET, request, String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Spring");
    }

    @Test
    void accessProtectedEndpoint_WithoutToken_ShouldReturnUnauthorized() {
        // When
        ResponseEntity<String> response = restTemplate.getForEntity(
            baseUrl + "/books", String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void rateLimiting_ShouldPreventExcessiveRequests() {
        // Given
        HttpEntity<?> request = new HttpEntity<>(createAuthHeaders());

        // When - Make multiple requests rapidly
        boolean rateLimited = false;
        for (int i = 0; i < 150; i++) { // Exceed the rate limit
            ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/books", HttpMethod.GET, request, String.class);

            if (response.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                rateLimited = true;
                break;
            }
        }

        // Then
        assertThat(rateLimited).isTrue();
    }

    private void createTestBooks() {
        Book book1 = new Book("Spring Boot Guide", "John Doe", "978-1-111111-11-1", "Technology", "Tech Press");
        book1.setTotalCopies(5);
        book1.setAvailableCopies(5);
        bookRepository.save(book1);

        Book book2 = new Book("Java Programming", "Jane Smith", "978-2-222222-22-2", "Technology", "Code Publishers");
        book2.setTotalCopies(3);
        book2.setAvailableCopies(3);
        bookRepository.save(book2);
    }

    private Book createTestBook() {
        Book book = new Book("Test Book", "Test Author", "978-0-000000-00-0", "Fiction", "Test Publisher");
        book.setTotalCopies(10);
        book.setAvailableCopies(10);
        return bookRepository.save(book);
    }
}
