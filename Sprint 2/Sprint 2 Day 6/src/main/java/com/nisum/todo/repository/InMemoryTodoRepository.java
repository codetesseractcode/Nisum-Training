package com.nisum.todo.repository;

import com.nisum.todo.model.Todo;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryTodoRepository implements TodoRepository {

    private final Map<Long, Todo> todoMap = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<Todo> findAll() {
        return new ArrayList<>(todoMap.values());
    }

    @Override
    public Optional<Todo> findById(Long id) {
        return Optional.ofNullable(todoMap.get(id));
    }

    @Override
    public Todo save(Todo todo) {
        if (todo.getId() == null) {
            todo.setId(idGenerator.getAndIncrement());
        }
        todo.setUpdatedAt(new Date());
        todoMap.put(todo.getId(), todo);
        return todo;
    }

    @Override
    public void deleteById(Long id) {
        todoMap.remove(id);
    }

    @Override
    public void update(Todo todo) {
        if (todo.getId() != null && todoMap.containsKey(todo.getId())) {
            todo.setUpdatedAt(new Date());
            todoMap.put(todo.getId(), todo);
        }
    }
}
