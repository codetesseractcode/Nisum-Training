package com.nisum.todo.service;

import com.nisum.todo.model.Todo;
import com.nisum.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    @Override
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public void updateTodo(Todo todo) {
        todo.setUpdatedAt(new Date());
        todoRepository.update(todo);
    }

    @Override
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public void toggleTodoCompletion(Long id) {
        Optional<Todo> todoOptional = todoRepository.findById(id);
        todoOptional.ifPresent(todo -> {
            todo.setCompleted(!todo.isCompleted());
            todo.setUpdatedAt(new Date());
            todoRepository.update(todo);
        });
    }
}
