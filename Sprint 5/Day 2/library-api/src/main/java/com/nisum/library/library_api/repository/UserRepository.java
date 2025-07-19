package com.nisum.library.library_api.repository;

import com.nisum.library.library_api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity operations.
 * Provides secure data access methods with SQL injection prevention.
 *
 * @author Nisum Library Team
 * @version 1.0
 * @since 2025-07-19
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by username (case-insensitive).
     * Uses parameterized query to prevent SQL injection.
     *
     * @param username the username to search for
     * @return Optional containing the user if found
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:username)")
    Optional<User> findByUsernameIgnoreCase(@Param("username") String username);

    /**
     * Finds a user by username (exact match).
     *
     * @param username the username to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by email address (case-insensitive).
     * Uses parameterized query to prevent SQL injection.
     *
     * @param email the email address to search for
     * @return Optional containing the user if found
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(@Param("email") String email);

    /**
     * Finds a user by email (exact match).
     *
     * @param email the email to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by username or email (case-insensitive).
     *
     * @param username the username to search for
     * @param email the email to search for
     * @return Optional containing the user if found
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:username) OR LOWER(u.email) = LOWER(:email)")
    Optional<User> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    /**
     * Checks if a username already exists (case-insensitive).
     *
     * @param username the username to check
     * @return true if username exists, false otherwise
     */
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE LOWER(u.username) = LOWER(:username)")
    boolean existsByUsernameIgnoreCase(@Param("username") String username);

    /**
     * Checks if an email already exists (case-insensitive).
     *
     * @param email the email to check
     * @return true if email exists, false otherwise
     */
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    boolean existsByEmailIgnoreCase(@Param("email") String email);

    /**
     * Finds users by role with pagination.
     *
     * @param role the user role to filter by
     * @param pageable pagination information
     * @return Page of users with the specified role
     */
    @Query("SELECT u FROM User u WHERE u.role = :role")
    Page<User> findByRole(@Param("role") User.Role role, Pageable pageable);

    /**
     * Finds enabled users with pagination.
     *
     * @param enabled the enabled status to filter by
     * @param pageable pagination information
     * @return Page of users with the specified enabled status
     */
    Page<User> findByEnabled(boolean enabled, Pageable pageable);

    /**
     * Searches users by name (first name or last name) containing the search term.
     * Case-insensitive search to prevent issues with user input.
     *
     * @param searchTerm the term to search for in names
     * @param pageable pagination information
     * @return Page of users matching the search criteria
     */
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<User> findByNameContaining(@Param("searchTerm") String searchTerm, Pageable pageable);
}
