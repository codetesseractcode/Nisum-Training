package com.nisum.todo.model;

import java.util.Date;

/**
 * Model class representing a Todo item
 */
public class Todo {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private Date createdDate;
    private Date dueDate;
    private String priority; // HIGH, MEDIUM, LOW

    // Default constructor
    public Todo() {
        this.createdDate = new Date();
        this.completed = false;
    }

    // Constructor with required fields
    public Todo(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdDate = new Date();
        this.completed = false;
    }

    // Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", createdDate=" + createdDate +
                ", dueDate=" + dueDate +
                ", priority='" + priority + '\'' +
                '}';
    }
}
