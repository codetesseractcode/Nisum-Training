package com.nisum.todo.service;

import com.nisum.todo.model.Todo;
import com.nisum.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of TodoService interface
 * Following Single Responsibility Principle - handles business logic only
 * Following Dependency Inversion Principle - depends on abstraction not implementation
 */
@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    // Constructor Injection follows Dependency Inversion principle
    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    @Override
    public void createTodo(Todo todo) {
        todoRepository.save(todo);
    }

    @Override
    public void updateTodo(Todo todo) {
        todoRepository.update(todo);
    }

    @Override
    public void deleteTodo(Long id) {
        todoRepository.delete(id);
    }

    @Override
    public List<Todo> getCompletedTodos() {
        return todoRepository.findAll().stream()
                .filter(Todo::isCompleted)
                .collect(Collectors.toList());
    }

    @Override
    public List<Todo> getPendingTodos() {
        return todoRepository.findAll().stream()
                .filter(todo -> !todo.isCompleted())
                .collect(Collectors.toList());
    }
}
