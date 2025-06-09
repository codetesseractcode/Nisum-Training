package com.nisum.todo.repository;

import com.nisum.todo.model.Todo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory implementation of TodoRepository
 * Following Single Responsibility Principle - handles only data storage and retrieval
 */
@Repository
public class InMemoryTodoRepository implements TodoRepository {
    // Using a map to store todos with id as the key
    private final Map<Long, Todo> todoMap = new HashMap<>();
    // Atomic counter for generating unique IDs
    private final AtomicLong counter = new AtomicLong(1);

    @Override
    public List<Todo> findAll() {
        return new ArrayList<>(todoMap.values());
    }

    @Override
    public Todo findById(Long id) {
        return todoMap.get(id);
    }

    @Override
    public void save(Todo todo) {
        if (todo.getId() == null) {
            todo.setId(counter.getAndIncrement());
        }
        todoMap.put(todo.getId(), todo);
    }

    @Override
    public void update(Todo todo) {
        if (todo.getId() != null && todoMap.containsKey(todo.getId())) {
            todoMap.put(todo.getId(), todo);
        }
    }

    @Override
    public void delete(Long id) {
        todoMap.remove(id);
    }
}
