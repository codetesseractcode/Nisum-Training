package com.nisum.todo.service;

import com.nisum.todo.model.Todo;
import java.util.List;
import java.util.Optional;

public interface TodoService {
    List<Todo> getAllTodos();
    Optional<Todo> getTodoById(Long id);
    Todo createTodo(Todo todo);
    void updateTodo(Todo todo);
    void deleteTodo(Long id);
    void toggleTodoCompletion(Long id);
}
