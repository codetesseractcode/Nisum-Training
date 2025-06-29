package com.nisum.userDemo;

import java.util.Objects;

/**
 * Immutable User class following SOLID principles:
 * - Single Responsibility: Only represents user data
 * - YAGNI: Only includes what's actually needed
 */
public class User {
    private final String name;
    private final String email;
    private final int age;

    // Constructor with validation
    public User(String name, String email, int age) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.age = age;
    }

    // Only getters - immutable object
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
               Objects.equals(name, user.name) &&
               Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, age);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
