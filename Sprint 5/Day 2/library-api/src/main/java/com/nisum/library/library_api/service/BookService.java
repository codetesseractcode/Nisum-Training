package com.nisum.library.library_api.service;

import com.nisum.library.library_api.dto.BookRequest;
import com.nisum.library.library_api.exception.ResourceNotFoundException;
import com.nisum.library.library_api.model.Book;
import com.nisum.library.library_api.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing books in the library system.
 * Provides comprehensive book management with caching capabilities.
 */
@Service
@Transactional
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Creates a new book.
     */
    public Book createBook(BookRequest bookRequest) {
        logger.info("Creating new book: {}", bookRequest.getTitle());

        // Check if ISBN already exists
        if (bookRepository.findByIsbn(bookRequest.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("Book with ISBN " + bookRequest.getIsbn() + " already exists");
        }

        Book book = mapToEntity(bookRequest);
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());

        Book savedBook = bookRepository.save(book);
        logger.info("Successfully created book with ID: {}", savedBook.getId());

        return savedBook;
    }

    /**
     * Finds a book by ID.
     */
    @Cacheable(value = "books", key = "#id")
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    /**
     * Finds a book by ISBN.
     */
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));
    }

    /**
     * Gets all books with pagination.
     */
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    /**
     * Gets available books with pagination.
     */
    public Page<Book> getAvailableBooks(Pageable pageable) {
        return bookRepository.findAvailableBooks(pageable);
    }

    /**
     * Updates a book.
     */
    @CacheEvict(value = "books", key = "#id")
    public Book updateBook(Long id, BookRequest bookRequest) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        // Check if ISBN is being changed and if new ISBN already exists
        if (!existingBook.getIsbn().equals(bookRequest.getIsbn()) &&
            bookRepository.findByIsbn(bookRequest.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("Book with ISBN " + bookRequest.getIsbn() + " already exists");
        }

        // Update book fields
        existingBook.setTitle(bookRequest.getTitle());
        existingBook.setAuthor(bookRequest.getAuthor());
        existingBook.setIsbn(bookRequest.getIsbn());
        existingBook.setCategory(bookRequest.getCategory());
        existingBook.setDescription(bookRequest.getDescription());
        existingBook.setTotalCopies(bookRequest.getTotalCopies());
        existingBook.setUpdatedAt(LocalDateTime.now());

        Book updatedBook = bookRepository.save(existingBook);
        logger.info("Updated book: {}", updatedBook.getTitle());

        return updatedBook;
    }

    /**
     * Searches books by multiple criteria.
     *
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of books matching the search criteria
     */
    @Transactional(readOnly = true)
    public Page<Book> searchBooks(String searchTerm, Pageable pageable) {
        return bookRepository.searchBooks(searchTerm, pageable);
    }

    /**
     * Finds books by category.
     *
     * @param category the category
     * @param pageable pagination information
     * @return page of books in the category
     */
    @Transactional(readOnly = true)
    public Page<Book> findByCategory(String category, Pageable pageable) {
        return bookRepository.findByCategoryIgnoreCase(category, pageable);
    }

    /**
     * Finds books by author.
     *
     * @param author the author name
     * @param pageable pagination information
     * @return page of books by the author
     */
    @Transactional(readOnly = true)
    public Page<Book> findByAuthor(String author, Pageable pageable) {
        return bookRepository.findByAuthorContainingIgnoreCase(author, pageable);
    }

    /**
     * Borrows a book copy.
     *
     * @param bookId the book ID
     * @return the updated book
     */
    @CacheEvict(value = {"books", "availableBooks"}, allEntries = true)
    public Book borrowBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        book.borrowCopy();

        Book updatedBook = bookRepository.save(book);
        logger.info("Book borrowed: {} (Available copies: {})", book.getTitle(), book.getAvailableCopies());

        return updatedBook;
    }

    /**
     * Returns a borrowed book copy.
     *
     * @param bookId the book ID
     * @return the updated book
     */
    @CacheEvict(value = {"books", "availableBooks"}, allEntries = true)
    public Book returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        book.returnCopy();

        Book updatedBook = bookRepository.save(book);
        logger.info("Book returned: {} (Available copies: {})", book.getTitle(), book.getAvailableCopies());

        return updatedBook;
    }

    /**
     * Gets all distinct categories.
     *
     * @return list of categories
     */
    @Transactional(readOnly = true)
    public List<String> getAllCategories() {
        return bookRepository.findDistinctCategories();
    }

    /**
     * Gets all distinct authors.
     *
     * @return list of authors
     */
    @Transactional(readOnly = true)
    public List<String> getAllAuthors() {
        return bookRepository.findDistinctAuthors();
    }

    /**
     * Deletes a book.
     *
     * @param id the book ID
     */
    @CacheEvict(value = {"books", "availableBooks"}, allEntries = true)
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        bookRepository.delete(book);
        logger.info("Deleted book: {}", book.getTitle());
    }

    /**
     * Gets count of available books.
     *
     * @return count of available books
     */
    @Transactional(readOnly = true)
    public long getAvailableBooksCount() {
        return bookRepository.countAvailableBooks();
    }

    /**
     * Maps BookRequest data to Book entity.
     *
     * @param request the book request
     * @param book the book entity
     */
    private void mapBookRequestToEntity(BookRequest request, Book book) {
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setCategory(request.getCategory());
        book.setDescription(request.getDescription());
        book.setPublisher(request.getPublisher());
        book.setPublicationDate(request.getPublicationDate());
        book.setPageCount(request.getPageCount());
        book.setPrice(request.getPrice());
        book.setShelfLocation(request.getShelfLocation());

        if (request.getTotalCopies() != null) {
            // If updating total copies, adjust available copies proportionally
            if (book.getTotalCopies() != null && !book.getTotalCopies().equals(request.getTotalCopies())) {
                int difference = request.getTotalCopies() - book.getTotalCopies();
                book.setAvailableCopies(Math.max(0, book.getAvailableCopies() + difference));
            }
            book.setTotalCopies(request.getTotalCopies());
        }
    }

    /**
     * Maps BookRequest data to Book entity.
     *
     * @param request the book request
     * @return the book entity
     */
    private Book mapToEntity(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setCategory(request.getCategory());
        book.setDescription(request.getDescription());
        book.setPublisher(request.getPublisher());
        book.setPublicationDate(request.getPublicationDate());
        book.setPageCount(request.getPageCount());
        book.setPrice(request.getPrice());
        book.setShelfLocation(request.getShelfLocation());
        book.setTotalCopies(request.getTotalCopies());
        book.setAvailableCopies(request.getTotalCopies()); // Initially, available copies equal total copies
        book.setStatus(Book.BookStatus.AVAILABLE);

        return book;
    }
}
