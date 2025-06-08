package com.nisum.todo.repository;

import com.nisum.todo.model.Todo;
import java.util.List;

/**
 * Repository interface for Todo operations
 * Following Interface Segregation Principle
 */
public interface TodoRepository {
    List<Todo> findAll();
    Todo findById(Long id);
    void save(Todo todo);
    void update(Todo todo);
    void delete(Long id);
}
