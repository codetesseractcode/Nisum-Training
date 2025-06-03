package com.nisum.model;

import com.nisum.dao.DocumentDAO;

/**
 * TextEditor class responsible for editing documents.
 * Following SRP by focusing only on editing functionality.
 * Following DIP by depending on abstractions (SpellChecker, DocumentDAO).
 */
public class TextEditor {
    private SpellChecker spellChecker;
    private DocumentDAO documentDAO;
    private String name;

    // Default constructor
    public TextEditor() {
        System.out.println("TextEditor default constructor called");
    }

    // Constructor injection for SpellChecker
    public TextEditor(SpellChecker spellChecker) {
        System.out.println("TextEditor constructor with SpellChecker called");
        this.spellChecker = spellChecker;
    }

    // Full constructor injection (both dependencies)
    public TextEditor(SpellChecker spellChecker, DocumentDAO documentDAO) {
        System.out.println("TextEditor constructor with SpellChecker and DocumentDAO called");
        this.spellChecker = spellChecker;
        this.documentDAO = documentDAO;
    }

    // Setter injection for SpellChecker
    public void setSpellChecker(SpellChecker spellChecker) {
        System.out.println("TextEditor setSpellChecker called");
        this.spellChecker = spellChecker;
    }

    // Setter injection for DocumentDAO
    public void setDocumentDAO(DocumentDAO documentDAO) {
        System.out.println("TextEditor setDocumentDAO called");
        this.documentDAO = documentDAO;
    }

    public void setName(String name) {
        System.out.println("TextEditor setName called with: " + name);
        this.name = name;
    }

    /**
     * Spell check text content
     * @param text the text to check
     */
    public void spellCheck(String text) {
        System.out.println("TextEditor '" + name + "' is processing text: " + text);
        if (spellChecker != null) {
            spellChecker.checkSpelling(text);
        } else {
            System.out.println("No spellChecker available");
        }
    }

    /**
     * Create a new document
     * @param title the document title
     * @param content the document content
     * @return the created document with ID
     */
    public Document createDocument(String title, String content) {
        Document document = new Document(title, content);

        // Spell check the content before saving
        if (spellChecker != null) {
            spellChecker.checkSpelling(content);
        }

        // Save the document using DAO
        if (documentDAO != null) {
            return documentDAO.save(document);
        } else {
            System.out.println("No documentDAO available, document not saved");
            return document;
        }
    }

    /**
     * Open a document by ID
     * @param id the document ID
     * @return the document if found
     */
    public Document openDocument(Long id) {
        if (documentDAO != null) {
            return documentDAO.findById(id);
        } else {
            System.out.println("No documentDAO available, cannot open document");
            return null;
        }
    }

    /**
     * Save changes to an existing document
     * @param document the document to update
     * @return the updated document
     */
    public Document saveDocument(Document document) {
        // Spell check the content before saving
        if (spellChecker != null && document.getContent() != null) {
            spellChecker.checkSpelling(document.getContent());
        }

        // Update the document using DAO
        if (documentDAO != null) {
            return documentDAO.update(document);
        } else {
            System.out.println("No documentDAO available, document not saved");
            return document;
        }
    }
}
