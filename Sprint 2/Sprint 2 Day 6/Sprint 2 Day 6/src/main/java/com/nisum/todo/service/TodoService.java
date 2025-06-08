package com.nisum.todo.service;

import com.nisum.todo.model.Todo;
import java.util.List;

/**
 * Service interface for Todo operations
 * Following Interface Segregation Principle
 */
public interface TodoService {
    List<Todo> getAllTodos();
    Todo getTodoById(Long id);
    void createTodo(Todo todo);
    void updateTodo(Todo todo);
    void deleteTodo(Long id);
    List<Todo> getCompletedTodos();
    List<Todo> getPendingTodos();
}
