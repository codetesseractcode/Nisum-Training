package com.example.userservice.service;

import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.model.Email;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailSender emailSender;

    public UserService(UserRepository userRepository, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.emailSender = emailSender;
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id);

        if (user == null) {
            handleMissingUser(id);
            throw new UserNotFoundException("User with id " + id + " not found");
        }

        // Send notification email when user is found
        sendUserFoundNotification(user);

        return user;
    }

    public void handleMissingUser(Long id) {
        // Fallback method for handling missing users
        System.out.println("Handling missing user with id: " + id);
        // Additional fallback logic can be added here
    }

    private void sendUserFoundNotification(User user) {
        Email email = new Email(
            user.getEmail(),
            "User Account Access Notification",
            "Your user account has been accessed successfully."
        );
        emailSender.send(email);
    }
}
