package com.nisum.library.library_api.controller;

import com.nisum.library.library_api.dto.BookRequest;
import com.nisum.library.library_api.model.Book;
import com.nisum.library.library_api.service.BookService;
import com.nisum.library.library_api.util.RateLimitUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for book management operations.
 * Implements comprehensive CRUD operations with security and rate limiting.
 *
 * @author Nisum Library Team
 * @version 1.0
 * @since 2025-07-19
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Books", description = "Book management APIs")
@SecurityRequirement(name = "bearerAuth")
public class BookController {

    private final BookService bookService;
    private final RateLimitUtil rateLimitUtil;

    public BookController(BookService bookService, RateLimitUtil rateLimitUtil) {
        this.bookService = bookService;
        this.rateLimitUtil = rateLimitUtil;
    }

    /**
     * Gets all books with pagination and sorting.
     *
     * @param page page number (0-based)
     * @param size page size
     * @param sortBy field to sort by
     * @param sortDir sort direction (asc/desc)
     * @param request HTTP request for rate limiting
     * @return paginated list of books
     */
    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieve all books with pagination")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN', 'USER')")
    public ResponseEntity<Page<Book>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            HttpServletRequest request) {

        // Apply rate limiting
        rateLimitUtil.checkRateLimit(request);

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Book> books = bookService.getAllBooks(pageable);

        return ResponseEntity.ok(books);
    }

    /**
     * Gets available books only.
     *
     * @param page page number
     * @param size page size
     * @param request HTTP request for rate limiting
     * @return paginated list of available books
     */
    @GetMapping("/available")
    @Operation(summary = "Get available books", description = "Retrieve only available books")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN', 'USER')")
    public ResponseEntity<Page<Book>> getAvailableBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        rateLimitUtil.checkRateLimit(request);

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookService.getAvailableBooks(pageable);

        return ResponseEntity.ok(books);
    }

    /**
     * Gets a book by ID.
     *
     * @param id the book ID
     * @param request HTTP request for rate limiting
     * @return the book details
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Retrieve a specific book by its ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN', 'USER')")
    public ResponseEntity<Book> getBookById(
            @Parameter(description = "Book ID") @PathVariable Long id,
            HttpServletRequest request) {

        rateLimitUtil.checkRateLimit(request);

        Book book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }

    /**
     * Searches books by title, author, or category.
     *
     * @param query search query
     * @param page page number
     * @param size page size
     * @param request HTTP request for rate limiting
     * @return paginated search results
     */
    @GetMapping("/search")
    @Operation(summary = "Search books", description = "Search books by title, author, or category")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN', 'USER')")
    public ResponseEntity<Page<Book>> searchBooks(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        rateLimitUtil.checkRateLimit(request);

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookService.searchBooks(query, pageable);

        return ResponseEntity.ok(books);
    }

    /**
     * Gets books by category.
     *
     * @param category the category name
     * @param page page number
     * @param size page size
     * @param request HTTP request for rate limiting
     * @return paginated list of books in category
     */
    @GetMapping("/category/{category}")
    @Operation(summary = "Get books by category", description = "Retrieve books in a specific category")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN', 'USER')")
    public ResponseEntity<Page<Book>> getBooksByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        rateLimitUtil.checkRateLimit(request);

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookService.findByCategory(category, pageable);

        return ResponseEntity.ok(books);
    }

    /**
     * Creates a new book (Admin/Librarian only).
     *
     * @param bookRequest the book creation request
     * @param request HTTP request for rate limiting
     * @return the created book
     */
    @PostMapping
    @Operation(summary = "Create new book", description = "Add a new book to the library")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<Book> createBook(
            @Valid @RequestBody BookRequest bookRequest,
            HttpServletRequest request) {

        rateLimitUtil.checkRateLimit(request);

        Book book = bookService.createBook(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    /**
     * Updates an existing book (Admin/Librarian only).
     *
     * @param id the book ID
     * @param bookRequest the update request
     * @param request HTTP request for rate limiting
     * @return the updated book
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update book", description = "Update an existing book")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequest bookRequest,
            HttpServletRequest request) {

        rateLimitUtil.checkRateLimit(request);

        Book book = bookService.updateBook(id, bookRequest);
        return ResponseEntity.ok(book);
    }

    /**
     * Borrows a book.
     *
     * @param id the book ID
     * @param request HTTP request for rate limiting
     * @return success message
     */
    @PostMapping("/{id}/borrow")
    @Operation(summary = "Borrow book", description = "Borrow a book from the library")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN', 'USER')")
    public ResponseEntity<Map<String, Object>> borrowBook(
            @PathVariable Long id,
            HttpServletRequest request) {

        rateLimitUtil.checkRateLimit(request);

        Book book = bookService.borrowBook(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Book borrowed successfully");
        response.put("book", book);
        response.put("availableCopies", book.getAvailableCopies());

        return ResponseEntity.ok(response);
    }

    /**
     * Returns a borrowed book.
     *
     * @param id the book ID
     * @param request HTTP request for rate limiting
     * @return success message
     */
    @PostMapping("/{id}/return")
    @Operation(summary = "Return book", description = "Return a borrowed book to the library")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN', 'USER')")
    public ResponseEntity<Map<String, Object>> returnBook(
            @PathVariable Long id,
            HttpServletRequest request) {

        rateLimitUtil.checkRateLimit(request);

        Book book = bookService.returnBook(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Book returned successfully");
        response.put("book", book);
        response.put("availableCopies", book.getAvailableCopies());

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a book (Admin only).
     *
     * @param id the book ID
     * @param request HTTP request for rate limiting
     * @return success message
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book", description = "Remove a book from the library")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteBook(
            @PathVariable Long id,
            HttpServletRequest request) {

        rateLimitUtil.checkRateLimit(request);

        bookService.deleteBook(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Book deleted successfully");

        return ResponseEntity.ok(response);
    }

    /**
     * Gets all categories.
     *
     * @param request HTTP request for rate limiting
     * @return list of categories
     */
    @GetMapping("/categories")
    @Operation(summary = "Get all categories", description = "Retrieve all book categories")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN', 'USER')")
    public ResponseEntity<List<String>> getAllCategories(HttpServletRequest request) {
        rateLimitUtil.checkRateLimit(request);

        List<String> categories = bookService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * Gets all authors.
     *
     * @param request HTTP request for rate limiting
     * @return list of authors
     */
    @GetMapping("/authors")
    @Operation(summary = "Get all authors", description = "Retrieve all book authors")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN', 'USER')")
    public ResponseEntity<List<String>> getAllAuthors(HttpServletRequest request) {
        rateLimitUtil.checkRateLimit(request);

        List<String> authors = bookService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }
}
