package com.nisum.beans;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Bean class to demonstrate different scopes.
 * Each instance will have a unique ID and creation timestamp
 * to help visualize bean lifecycle.
 */
public class ScopeBean {

    private String scopeName;
    private final String id;
    private final LocalDateTime creationTime;

    public ScopeBean() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.creationTime = LocalDateTime.now();
    }

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public String toString() {
        return "ScopeBean{" +
                "scope='" + scopeName + '\'' +
                ", id='" + id + '\'' +
                ", created=" + creationTime +
                '}';
    }
}
