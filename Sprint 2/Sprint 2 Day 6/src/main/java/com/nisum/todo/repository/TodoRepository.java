package com.nisum.todo.repository;

import com.nisum.todo.model.Todo;
import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    List<Todo> findAll();
    Optional<Todo> findById(Long id);
    Todo save(Todo todo);
    void deleteById(Long id);
    void update(Todo todo);
}
