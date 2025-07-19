package com.nisum.library.library_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.library.library_api.dto.BookRequest;
import com.nisum.library.library_api.model.Book;
import com.nisum.library.library_api.service.BookService;
import com.nisum.library.library_api.util.RateLimitUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for BookController using @WebMvcTest.
 * Tests controller layer in isolation with mocked dependencies.
 *
 * @author Nisum Library Team
 * @version 1.0
 * @since 2025-07-19
 */
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private RateLimitUtil rateLimitUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private Book testBook;
    private BookRequest testBookRequest;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setIsbn("978-0-123456-78-9");
        testBook.setCategory("Fiction");
        testBook.setDescription("A test book");
        testBook.setPublisher("Test Publisher");
        testBook.setPublicationDate(LocalDate.of(2023, 1, 1));
        testBook.setPageCount(200);
        testBook.setPrice(new BigDecimal("19.99"));
        testBook.setTotalCopies(5);
        testBook.setAvailableCopies(3);

        testBookRequest = new BookRequest();
        testBookRequest.setTitle("Test Book");
        testBookRequest.setAuthor("Test Author");
        testBookRequest.setIsbn("978-0-123456-78-9");
        testBookRequest.setCategory("Fiction");
        testBookRequest.setDescription("A test book");
        testBookRequest.setPublisher("Test Publisher");
        testBookRequest.setPublicationDate(LocalDate.of(2023, 1, 1));
        testBookRequest.setPageCount(200);
        testBookRequest.setPrice(new BigDecimal("19.99"));
        testBookRequest.setTotalCopies(5);
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllBooks_ShouldReturnPageOfBooks() throws Exception {
        // Given
        List<Book> books = Arrays.asList(testBook);
        Page<Book> bookPage = new PageImpl<>(books, PageRequest.of(0, 10), 1);
        when(bookService.getAllBooks(any())).thenReturn(bookPage);

        // When & Then
        mockMvc.perform(get("/api/v1/books")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].title").value("Test Book"))
                .andExpect(jsonPath("$.content[0].author").value("Test Author"));

        verify(rateLimitUtil).checkRateLimit(any());
        verify(bookService).getAllBooks(any());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getBookById_ShouldReturnBook() throws Exception {
        // Given
        when(bookService.findById(1L)).thenReturn(testBook);

        // When & Then
        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"));

        verify(rateLimitUtil).checkRateLimit(any());
        verify(bookService).findById(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createBook_ShouldReturnCreatedBook() throws Exception {
        // Given
        when(bookService.createBook(any(BookRequest.class))).thenReturn(testBook);

        // When & Then
        mockMvc.perform(post("/api/v1/books")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBookRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"));

        verify(rateLimitUtil).checkRateLimit(any());
        verify(bookService).createBook(any(BookRequest.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    void createBook_WithoutAdminRole_ShouldReturnForbidden() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/v1/books")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBookRequest)))
                .andExpect(status().isForbidden());

        verify(bookService, never()).createBook(any());
    }

    @Test
    @WithMockUser(roles = "USER")
    void borrowBook_ShouldReturnSuccessMessage() throws Exception {
        // Given
        when(bookService.borrowBook(1L)).thenReturn(testBook);

        // When & Then
        mockMvc.perform(post("/api/v1/books/1/borrow")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book borrowed successfully"))
                .andExpect(jsonPath("$.book.id").value(1));

        verify(rateLimitUtil).checkRateLimit(any());
        verify(bookService).borrowBook(1L);
    }

    @Test
    @WithMockUser(roles = "USER")
    void searchBooks_ShouldReturnMatchingBooks() throws Exception {
        // Given
        List<Book> books = Arrays.asList(testBook);
        Page<Book> bookPage = new PageImpl<>(books, PageRequest.of(0, 10), 1);
        when(bookService.searchBooks(eq("test"), any())).thenReturn(bookPage);

        // When & Then
        mockMvc.perform(get("/api/v1/books/search")
                .param("query", "test")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].title").value("Test Book"));

        verify(rateLimitUtil).checkRateLimit(any());
        verify(bookService).searchBooks(eq("test"), any());
    }

    @Test
    void getAllBooks_WithoutAuthentication_ShouldReturnUnauthorized() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isUnauthorized());

        verify(bookService, never()).getAllBooks(any());
    }

    @Test
    @WithMockUser(roles = "LIBRARIAN")
    void updateBook_WithLibrarianRole_ShouldReturnUpdatedBook() throws Exception {
        // Given
        when(bookService.updateBook(eq(1L), any(BookRequest.class))).thenReturn(testBook);

        // When & Then
        mockMvc.perform(put("/api/v1/books/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBookRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));

        verify(rateLimitUtil).checkRateLimit(any());
        verify(bookService).updateBook(eq(1L), any(BookRequest.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteBook_WithAdminRole_ShouldReturnSuccessMessage() throws Exception {
        // Given
        doNothing().when(bookService).deleteBook(1L);

        // When & Then
        mockMvc.perform(delete("/api/v1/books/1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book deleted successfully"));

        verify(rateLimitUtil).checkRateLimit(any());
        verify(bookService).deleteBook(1L);
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteBook_WithoutAdminRole_ShouldReturnForbidden() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/v1/books/1")
                .with(csrf()))
                .andExpect(status().isForbidden());

        verify(bookService, never()).deleteBook(any());
    }
}
