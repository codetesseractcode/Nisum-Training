package com.example.userservice.repository;

import com.example.userservice.model.User;

public interface UserRepository {
    User findById(Long id);
    User save(User user);
    void deleteById(Long id);
}
