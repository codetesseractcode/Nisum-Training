package com.nisum.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;

public class Student {
    private int id;

    @NotEmpty(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Course is required")
    private String course;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default constructor
    public Student() {
    }

    // Constructor with fields
    public Student(int id, String name, String email, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", course='" + course + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Component
    @Scope(WebApplicationContext.SCOPE_APPLICATION)
    public static class ApplicationScopedBean {
        private String message;
        private long creationTime;

        public ApplicationScopedBean() {
            this.creationTime = System.currentTimeMillis();
            this.message = "Application Scoped Bean";
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public long getCreationTime() {
            return creationTime;
        }
    }

    @Component
    @Scope("prototype")
    public static class PrototypeBean {
        private String message;
        private long creationTime;

        public PrototypeBean() {
            this.creationTime = System.currentTimeMillis();
            this.message = "Prototype Bean";
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public long getCreationTime() {
            return creationTime;
        }
    }

    @Component
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public static class RequestScopedBean {
        private String message;
        private long creationTime;

        public RequestScopedBean() {
            this.creationTime = System.currentTimeMillis();
            this.message = "Request Scoped Bean";
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public long getCreationTime() {
            return creationTime;
        }
    }

    @Component
    @Scope(WebApplicationContext.SCOPE_SESSION)
    public static class SessionScopedBean {
        private String message;
        private long creationTime;

        public SessionScopedBean() {
            this.creationTime = System.currentTimeMillis();
            this.message = "Session Scoped Bean";
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public long getCreationTime() {
            return creationTime;
        }
    }

    @Component
    @Scope("singleton")
    public static class SingletonBean {
        private String message;
        private long creationTime;

        public SingletonBean() {
            this.creationTime = System.currentTimeMillis();
            this.message = "Singleton Bean";
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public long getCreationTime() {
            return creationTime;
        }
    }
}
