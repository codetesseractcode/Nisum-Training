package com.nisum.question3.controller;

import com.nisum.question3.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // Simple in-memory storage for demonstration
    private final Map<Long, User> userStore = new HashMap<>();
    private long nextId = 1;

    // GET all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(new ArrayList<>(userStore.values()));
    }

    // GET a specific user
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        if (!userStore.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userStore.get(id));
    }

    // POST - Create a new user with validation
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        user.setId(nextId++);
        userStore.put(user.getId(), user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // PUT - Update an existing user with validation
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        if (!userStore.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        user.setId(id);
        userStore.put(id, user);
        return ResponseEntity.ok(user);
    }

    // DELETE - Remove a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userStore.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        userStore.remove(id);
        return ResponseEntity.noContent().build();
    }
}
