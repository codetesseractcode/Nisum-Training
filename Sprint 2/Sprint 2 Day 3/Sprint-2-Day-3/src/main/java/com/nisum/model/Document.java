package com.nisum.model;

import java.util.Date;

/**
 * Document class represents a text document in the editor.
 * Following SRP by focusing only on representing document data.
 */
public class Document {
    private Long id;
    private String title;
    private String content;
    private Date createdDate;
    private Date lastModifiedDate;

    // Default constructor
    public Document() {
        this.createdDate = new Date();
        this.lastModifiedDate = new Date();
    }

    // Parameterized constructor
    public Document(String title, String content) {
        this();
        this.title = title;
        this.content = content;
    }

    // Full constructor
    public Document(Long id, String title, String content, Date createdDate, Date lastModifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.lastModifiedDate = new Date(); // Update the modification date
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content length=" + (content != null ? content.length() : 0) +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
