package com.example.userservice.service;

import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.model.Email;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUserById_FirstCallThrowsException_SecondCallSucceeds() {
        // Arrange
        Long userId = 1L;
        User user = new User(userId, "John Doe", "john@example.com");

        // Stub repository to return null for first call, then user object for second call
        when(userRepository.findById(userId))
            .thenReturn(null)
            .thenReturn(user);

        // Act & Assert - First call should throw exception
        UserNotFoundException exception = assertThrows(
            UserNotFoundException.class,
            () -> userService.getUserById(userId)
        );

        // Verify exception message
        assertEquals("User with id " + userId + " not found", exception.getMessage());

        // Verify email sender was not called on first attempt
        verify(emailSender, never()).send(any(Email.class));

        // Act - Second call should succeed
        User result = userService.getUserById(userId);

        // Assert - Second call returns user
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());

        // Verify email is sent on successful retrieval
        ArgumentCaptor<Email> emailCaptor = ArgumentCaptor.forClass(Email.class);
        verify(emailSender, times(1)).send(emailCaptor.capture());

        // Assert the captured email argument and verify subject line
        Email capturedEmail = emailCaptor.getValue();
        assertNotNull(capturedEmail);
        assertEquals("john@example.com", capturedEmail.getTo());
        assertEquals("User Account Access Notification", capturedEmail.getSubject());
        assertEquals("Your user account has been accessed successfully.", capturedEmail.getBody());

        // Verify repository was called twice
        verify(userRepository, times(2)).findById(userId);
    }

    @Test
    void testGetUserById_UserFound_EmailSent() {
        // Arrange
        Long userId = 2L;
        User user = new User(userId, "Jane Smith", "jane@example.com");

        when(userRepository.findById(userId)).thenReturn(user);

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(user, result);

        // Verify email is sent and capture the argument
        ArgumentCaptor<Email> emailCaptor = ArgumentCaptor.forClass(Email.class);
        verify(emailSender, times(1)).send(emailCaptor.capture());

        Email capturedEmail = emailCaptor.getValue();
        assertEquals("User Account Access Notification", capturedEmail.getSubject());
        assertEquals("jane@example.com", capturedEmail.getTo());
    }

    @Test
    void testGetUserById_UserNotFound_ExceptionThrown() {
        // Arrange
        Long userId = 4L;
        when(userRepository.findById(userId)).thenReturn(null);

        // Act & Assert
        UserNotFoundException exception = assertThrows(
            UserNotFoundException.class,
            () -> userService.getUserById(userId)
        );

        assertEquals("User with id " + userId + " not found", exception.getMessage());
        verify(emailSender, never()).send(any(Email.class));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testHandleMissingUser_CallsCorrectly() {
        // Arrange
        Long userId = 3L;

        // Act - Test the fallback method directly
        assertDoesNotThrow(() -> userService.handleMissingUser(userId));

        // This test verifies that the handleMissingUser method executes without errors
        // In a real scenario, you might verify specific side effects or logging
    }

    @Test
    void testEmailCapture_VerifySubjectLine() {
        // Arrange
        Long userId = 5L;
        User user = new User(userId, "Alice Johnson", "alice@example.com");

        when(userRepository.findById(userId)).thenReturn(user);

        // Act
        userService.getUserById(userId);

        // Assert - Capture and verify the email argument, specifically the subject line
        ArgumentCaptor<Email> emailCaptor = ArgumentCaptor.forClass(Email.class);
        verify(emailSender).send(emailCaptor.capture());

        Email sentEmail = emailCaptor.getValue();
        assertEquals("User Account Access Notification", sentEmail.getSubject());
        assertEquals("alice@example.com", sentEmail.getTo());
        assertEquals("Your user account has been accessed successfully.", sentEmail.getBody());
    }
}
