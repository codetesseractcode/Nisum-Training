package com.nisum.bookapi.service;

import com.nisum.bookapi.dto.BookCreateRequest;
import com.nisum.bookapi.dto.BookResponse;
import com.nisum.bookapi.dto.BookUpdateRequest;
import com.nisum.bookapi.entity.Book;
import com.nisum.bookapi.exception.BookNotFoundException;
import com.nisum.bookapi.exception.DuplicateIsbnException;
import com.nisum.bookapi.mapper.BookMapper;
import com.nisum.bookapi.repository.BookRepository;
import com.nisum.bookapi.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private BookCreateRequest createRequest;
    private BookUpdateRequest updateRequest;
    private BookResponse bookResponse;

    @BeforeEach
    void setUp() {
        book = new Book("Test Book", "Test Author", "978-0123456789", 2023, new BigDecimal("29.99"));
        book.setId(1L);

        createRequest = new BookCreateRequest("Test Book", "Test Author", "978-0123456789", 2023, new BigDecimal("29.99"));
        updateRequest = new BookUpdateRequest("Updated Book", "Updated Author", null, 2024, new BigDecimal("39.99"));
        bookResponse = new BookResponse(1L, "Test Book", "Test Author", "978-0123456789", 2023, new BigDecimal("29.99"), null, null);
    }

    @Test
    void createBook_ShouldReturnBookResponse_WhenValidRequest() {
        // Given
        when(bookRepository.existsByIsbn(anyString())).thenReturn(false);
        when(bookMapper.toEntity(createRequest)).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toResponse(book)).thenReturn(bookResponse);

        // When
        BookResponse result = bookService.createBook(createRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Book");
        verify(bookRepository).existsByIsbn("978-0123456789");
        verify(bookRepository).save(any(Book.class));
        verify(bookMapper).toEntity(createRequest);
        verify(bookMapper).toResponse(book);
    }

    @Test
    void createBook_ShouldThrowDuplicateIsbnException_WhenIsbnExists() {
        // Given
        when(bookRepository.existsByIsbn(anyString())).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> bookService.createBook(createRequest))
                .isInstanceOf(DuplicateIsbnException.class)
                .hasMessageContaining("already exists");

        verify(bookRepository).existsByIsbn("978-0123456789");
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void getBookById_ShouldReturnBookResponse_WhenBookExists() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toResponse(book)).thenReturn(bookResponse);

        // When
        BookResponse result = bookService.getBookById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(bookRepository).findById(1L);
        verify(bookMapper).toResponse(book);
    }

    @Test
    void getBookById_ShouldThrowBookNotFoundException_WhenBookDoesNotExist() {
        // Given
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> bookService.getBookById(999L))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("not found");

        verify(bookRepository).findById(999L);
        verify(bookMapper, never()).toResponse(any(Book.class));
    }

    @Test
    void getAllBooks_ShouldReturnListOfBookResponses() {
        // Given
        Book book2 = new Book("Book 2", "Author 2", "978-0987654321", 2022, new BigDecimal("19.99"));
        List<Book> books = Arrays.asList(book, book2);

        BookResponse response2 = new BookResponse(2L, "Book 2", "Author 2", "978-0987654321", 2022, new BigDecimal("19.99"), null, null);

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.toResponse(book)).thenReturn(bookResponse);
        when(bookMapper.toResponse(book2)).thenReturn(response2);

        // When
        List<BookResponse> result = bookService.getAllBooks();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Book");
        assertThat(result.get(1).getTitle()).isEqualTo("Book 2");
        verify(bookRepository).findAll();
        verify(bookMapper, times(2)).toResponse(any(Book.class));
    }

    @Test
    void updateBook_ShouldReturnUpdatedBookResponse_WhenValidRequest() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toResponse(book)).thenReturn(bookResponse);

        // When
        BookResponse result = bookService.updateBook(1L, updateRequest);

        // Then
        assertThat(result).isNotNull();
        verify(bookRepository).findById(1L);
        verify(bookMapper).updateEntityFromRequest(updateRequest, book);
        verify(bookRepository).save(book);
        verify(bookMapper).toResponse(book);
    }

    @Test
    void updateBook_ShouldThrowBookNotFoundException_WhenBookDoesNotExist() {
        // Given
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> bookService.updateBook(999L, updateRequest))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("not found");

        verify(bookRepository).findById(999L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void deleteBook_ShouldDeleteBook_WhenBookExists() {
        // Given
        when(bookRepository.existsById(1L)).thenReturn(true);

        // When
        bookService.deleteBook(1L);

        // Then
        verify(bookRepository).existsById(1L);
        verify(bookRepository).deleteById(1L);
    }

    @Test
    void deleteBook_ShouldThrowBookNotFoundException_WhenBookDoesNotExist() {
        // Given
        when(bookRepository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> bookService.deleteBook(999L))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("not found");

        verify(bookRepository).existsById(999L);
        verify(bookRepository, never()).deleteById(any(Long.class));
    }
}
