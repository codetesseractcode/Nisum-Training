package com.nisum.library.library_api.service;

import com.nisum.library.library_api.dto.BookRequest;
import com.nisum.library.library_api.exception.ResourceNotFoundException;
import com.nisum.library.library_api.model.Book;
import com.nisum.library.library_api.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for BookService.
 * Tests business logic in isolation with mocked dependencies.
 *
 * @author Nisum Library Team
 * @version 1.0
 * @since 2025-07-19
 */
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void createBook_WithValidRequest_ShouldReturnCreatedBook() {
        // Given
        BookRequest request = new BookRequest();
        request.setTitle("Test Book");
        request.setAuthor("Test Author");
        request.setIsbn("978-0-123456-78-9");
        request.setCategory("Fiction");
        request.setPublisher("Test Publisher");
        request.setTotalCopies(5);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setTitle(request.getTitle());
        savedBook.setAuthor(request.getAuthor());
        savedBook.setIsbn(request.getIsbn());
        savedBook.setTotalCopies(5);
        savedBook.setAvailableCopies(5);

        when(bookRepository.findByIsbn(request.getIsbn())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        // When
        Book result = bookService.createBook(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Book");
        assertThat(result.getAuthor()).isEqualTo("Test Author");
        assertThat(result.getAvailableCopies()).isEqualTo(5);
        verify(bookRepository).findByIsbn(request.getIsbn());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void createBook_WithExistingIsbn_ShouldThrowException() {
        // Given
        BookRequest request = new BookRequest();
        request.setIsbn("978-0-123456-78-9");

        Book existingBook = new Book();
        when(bookRepository.findByIsbn(request.getIsbn())).thenReturn(Optional.of(existingBook));

        // When & Then
        assertThatThrownBy(() -> bookService.createBook(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");

        verify(bookRepository).findByIsbn(request.getIsbn());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void findById_WithValidId_ShouldReturnBook() {
        // Given
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Test Book");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // When
        Book result = bookService.findById(bookId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(bookId);
        assertThat(result.getTitle()).isEqualTo("Test Book");
        verify(bookRepository).findById(bookId);
    }

    @Test
    void findById_WithInvalidId_ShouldThrowResourceNotFoundException() {
        // Given
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> bookService.findById(bookId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("not found with id: " + bookId);

        verify(bookRepository).findById(bookId);
    }

    @Test
    void borrowBook_WithAvailableCopies_ShouldDecrementCount() {
        // Given
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Test Book");
        book.setTotalCopies(5);
        book.setAvailableCopies(3);
        book.setStatus(Book.BookStatus.AVAILABLE);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // When
        Book result = bookService.borrowBook(bookId);

        // Then
        assertThat(result.getAvailableCopies()).isEqualTo(2);
        verify(bookRepository).findById(bookId);
        verify(bookRepository).save(book);
    }

    @Test
    void borrowBook_WithNoAvailableCopies_ShouldThrowException() {
        // Given
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setAvailableCopies(0);
        book.setStatus(Book.BookStatus.AVAILABLE);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // When & Then
        assertThatThrownBy(() -> bookService.borrowBook(bookId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("No copies available");

        verify(bookRepository).findById(bookId);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void returnBook_WithBorrowedCopies_ShouldIncrementCount() {
        // Given
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTotalCopies(5);
        book.setAvailableCopies(2);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // When
        Book result = bookService.returnBook(bookId);

        // Then
        assertThat(result.getAvailableCopies()).isEqualTo(3);
        verify(bookRepository).findById(bookId);
        verify(bookRepository).save(book);
    }

    @Test
    void searchBooks_ShouldReturnMatchingBooks() {
        // Given
        String searchTerm = "test";
        PageRequest pageable = PageRequest.of(0, 10);
        Book book = new Book();
        book.setTitle("Test Book");
        Page<Book> bookPage = new PageImpl<>(Arrays.asList(book));

        when(bookRepository.searchBooks(searchTerm, pageable)).thenReturn(bookPage);

        // When
        Page<Book> result = bookService.searchBooks(searchTerm, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Test Book");
        verify(bookRepository).searchBooks(searchTerm, pageable);
    }

    @Test
    void deleteBook_WithValidId_ShouldDeleteBook() {
        // Given
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(book);

        // When
        bookService.deleteBook(bookId);

        // Then
        verify(bookRepository).findById(bookId);
        verify(bookRepository).delete(book);
    }
}
