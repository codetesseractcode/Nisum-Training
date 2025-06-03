package com.nisum.dao;

import com.nisum.model.Document;
import java.util.List;

/**
 * DocumentDAO interface defines the contract for Document data access operations.
 * Following Interface Segregation Principle by providing specific methods for Document operations.
 */
public interface DocumentDAO {

    /**
     * Save a document to the database
     * @param document the document to save
     * @return the saved document with generated ID
     */
    Document save(Document document);

    /**
     * Find a document by its ID
     * @param id the document ID
     * @return the document if found, null otherwise
     */
    Document findById(Long id);

    /**
     * Get all documents
     * @return list of all documents
     */
    List<Document> findAll();

    /**
     * Update an existing document
     * @param document the document to update
     * @return the updated document
     */
    Document update(Document document);

    /**
     * Delete a document by its ID
     * @param id the document ID to delete
     * @return true if deleted successfully, false otherwise
     */
    boolean deleteById(Long id);

    /**
     * Find documents by title (partial match)
     * @param title the title to search for
     * @return list of matching documents
     */
    List<Document> findByTitle(String title);
}
