package com.nisum.library.library_api.repository;

import com.nisum.library.library_api.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Book entity operations.
 * Implements secure data access patterns to prevent SQL injection.
 *
 * @author Nisum Library Team
 * @version 1.0
 * @since 2025-07-19
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Finds a book by ISBN.
     * Uses parameterized query to prevent SQL injection.
     *
     * @param isbn the ISBN to search for
     * @return Optional containing the book if found
     */
    Optional<Book> findByIsbn(@Param("isbn") String isbn);

    /**
     * Searches books by title containing the search term (case-insensitive).
     *
     * @param title the title search term
     * @param pageable pagination information
     * @return Page of books matching the title criteria
     */
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<Book> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);

    /**
     * Searches books by author containing the search term (case-insensitive).
     *
     * @param author the author search term
     * @param pageable pagination information
     * @return Page of books matching the author criteria
     */
    @Query("SELECT b FROM Book b WHERE LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))")
    Page<Book> findByAuthorContainingIgnoreCase(@Param("author") String author, Pageable pageable);

    /**
     * Finds books by category (case-insensitive).
     *
     * @param category the category to filter by
     * @param pageable pagination information
     * @return Page of books in the specified category
     */
    @Query("SELECT b FROM Book b WHERE LOWER(b.category) = LOWER(:category)")
    Page<Book> findByCategoryIgnoreCase(@Param("category") String category, Pageable pageable);

    /**
     * Finds available books (with available copies > 0).
     *
     * @param pageable pagination information
     * @return Page of available books
     */
    @Query("SELECT b FROM Book b WHERE b.availableCopies > 0 AND b.status = 'AVAILABLE'")
    Page<Book> findAvailableBooks(Pageable pageable);

    /**
     * Searches books by multiple criteria (title, author, or category).
     * Provides comprehensive search functionality while preventing SQL injection.
     *
     * @param searchTerm the term to search for
     * @param pageable pagination information
     * @return Page of books matching any of the criteria
     */
    @Query("SELECT b FROM Book b WHERE " +
           "LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(b.category) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Book> searchBooks(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Finds books by status.
     *
     * @param status the book status to filter by
     * @param pageable pagination information
     * @return Page of books with the specified status
     */
    Page<Book> findByStatus(Book.BookStatus status, Pageable pageable);

    /**
     * Gets all distinct categories for filtering purposes.
     *
     * @return List of distinct categories
     */
    @Query("SELECT DISTINCT b.category FROM Book b ORDER BY b.category")
    List<String> findDistinctCategories();

    /**
     * Gets all distinct authors for filtering purposes.
     *
     * @return List of distinct authors
     */
    @Query("SELECT DISTINCT b.author FROM Book b ORDER BY b.author")
    List<String> findDistinctAuthors();

    /**
     * Counts total available books.
     *
     * @return count of available books
     */
    @Query("SELECT COUNT(b) FROM Book b WHERE b.availableCopies > 0 AND b.status = 'AVAILABLE'")
    long countAvailableBooks();
}
