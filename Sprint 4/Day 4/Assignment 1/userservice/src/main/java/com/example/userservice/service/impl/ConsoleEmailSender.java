package com.example.userservice.service.impl;

import com.example.userservice.model.Email;
import com.example.userservice.service.EmailSender;
import org.springframework.stereotype.Service;

@Service
public class ConsoleEmailSender implements EmailSender {

    @Override
    public void send(Email email) {
        System.out.println("Sending email:");
        System.out.println("  To: " + email.getTo());
        System.out.println("  Subject: " + email.getSubject());
        System.out.println("  Body: " + email.getBody());
        System.out.println("Email sent successfully!");
    }
}
