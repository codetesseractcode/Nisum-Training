package com.nisum.bookapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.bookapi.dto.BookCreateRequest;
import com.nisum.bookapi.dto.BookUpdateRequest;
import com.nisum.bookapi.entity.Book;
import com.nisum.bookapi.repository.BookRepository;
import com.nisum.bookapi.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private String jwtToken;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        jwtToken = jwtUtil.generateToken("testuser");
    }

    @Test
    void createBook_ShouldReturnCreated_WhenValidRequest() throws Exception {
        BookCreateRequest request = new BookCreateRequest(
            "Test Book",
            "Test Author",
            "978-0123456789",
            2023,
            new BigDecimal("29.99")
        );

        mockMvc.perform(post("/api/books")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Test Book")))
                .andExpect(jsonPath("$.author", is("Test Author")))
                .andExpect(jsonPath("$.isbn", is("978-0123456789")))
                .andExpect(jsonPath("$.publicationYear", is(2023)))
                .andExpect(jsonPath("$.price", is(29.99)))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.createdAt", notNullValue()));
    }

    @Test
    void createBook_ShouldReturnBadRequest_WhenInvalidRequest() throws Exception {
        BookCreateRequest request = new BookCreateRequest(
            "", // Invalid: blank title
            "Test Author",
            "978-0123456789",
            -1, // Invalid: negative year
            new BigDecimal("-10.00") // Invalid: negative price
        );

        mockMvc.perform(post("/api/books")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasKey("title")))
                .andExpect(jsonPath("$.errors", hasKey("publicationYear")))
                .andExpect(jsonPath("$.errors", hasKey("price")));
    }

    @Test
    void createBook_ShouldReturnConflict_WhenDuplicateIsbn() throws Exception {
        // Create first book
        Book existingBook = new Book("Existing Book", "Author", "978-0123456789", 2022, new BigDecimal("19.99"));
        bookRepository.save(existingBook);

        BookCreateRequest request = new BookCreateRequest(
            "New Book",
            "New Author",
            "978-0123456789", // Same ISBN
            2023,
            new BigDecimal("29.99")
        );

        mockMvc.perform(post("/api/books")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", containsString("already exists")));
    }

    @Test
    void getBookById_ShouldReturnBook_WhenBookExists() throws Exception {
        Book book = new Book("Test Book", "Test Author", "978-0123456789", 2023, new BigDecimal("29.99"));
        Book savedBook = bookRepository.save(book);

        mockMvc.perform(get("/api/books/{id}", savedBook.getId())
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedBook.getId().intValue())))
                .andExpect(jsonPath("$.title", is("Test Book")))
                .andExpect(jsonPath("$.author", is("Test Author")));
    }

    @Test
    void getBookById_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/books/{id}", 999L)
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("not found")));
    }

    @Test
    void getAllBooks_ShouldReturnAllBooks() throws Exception {
        Book book1 = new Book("Book 1", "Author 1", "978-0123456781", 2022, new BigDecimal("19.99"));
        Book book2 = new Book("Book 2", "Author 2", "978-0123456782", 2023, new BigDecimal("24.99"));
        bookRepository.save(book1);
        bookRepository.save(book2);

        mockMvc.perform(get("/api/books")
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Book 1")))
                .andExpect(jsonPath("$[1].title", is("Book 2")));
    }

    @Test
    void updateBook_ShouldReturnUpdatedBook_WhenValidRequest() throws Exception {
        Book book = new Book("Original Title", "Original Author", "978-0123456789", 2022, new BigDecimal("19.99"));
        Book savedBook = bookRepository.save(book);

        BookUpdateRequest request = new BookUpdateRequest(
            "Updated Title",
            "Updated Author",
            null, // Don't update ISBN
            2023,
            new BigDecimal("29.99")
        );

        mockMvc.perform(put("/api/books/{id}", savedBook.getId())
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Title")))
                .andExpect(jsonPath("$.author", is("Updated Author")))
                .andExpect(jsonPath("$.isbn", is("978-0123456789"))) // Should remain unchanged
                .andExpect(jsonPath("$.publicationYear", is(2023)))
                .andExpect(jsonPath("$.price", is(29.99)));
    }

    @Test
    void deleteBook_ShouldReturnNoContent_WhenBookExists() throws Exception {
        Book book = new Book("Test Book", "Test Author", "978-0123456789", 2023, new BigDecimal("29.99"));
        Book savedBook = bookRepository.save(book);

        mockMvc.perform(delete("/api/books/{id}", savedBook.getId())
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());

        // Verify book is deleted
        mockMvc.perform(get("/api/books/{id}", savedBook.getId())
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBook_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        mockMvc.perform(delete("/api/books/{id}", 999L)
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void accessWithoutToken_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void accessWithInvalidToken_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/books")
                .header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isUnauthorized());
    }
}
