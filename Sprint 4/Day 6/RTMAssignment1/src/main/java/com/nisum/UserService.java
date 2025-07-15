package com.nisum;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class handling user operations
 * Implements the three requirements:
 * R1: User can log in
 * R2: User can reset password
 * R3: User can update profile
 */
public class UserService {
    private Map<String, User> userDatabase;

    public UserService() {
        this.userDatabase = new HashMap<>();
    }

    /**
     * R1: User can log in
     */
    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        User user = userDatabase.get(username);
        return user != null && user.getPassword().equals(password);
    }

    /**
     * R2: User can reset password
     */
    public boolean resetPassword(String username, String email, String newPassword) {
        if (username == null || email == null || newPassword == null) {
            return false;
        }

        User user = userDatabase.get(username);
        if (user != null && user.getEmail().equals(email)) {
            user.setPassword(newPassword);
            return true;
        }
        return false;
    }

    /**
     * R3: User can update profile
     */
    public boolean updateProfile(String username, String newProfile) {
        if (username == null || newProfile == null) {
            return false;
        }

        User user = userDatabase.get(username);
        if (user != null) {
            user.setProfile(newProfile);
            return true;
        }
        return false;
    }

    // Helper method for testing
    public void addUser(User user) {
        userDatabase.put(user.getUsername(), user);
    }

    public User getUser(String username) {
        return userDatabase.get(username);
    }
}
