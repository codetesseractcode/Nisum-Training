package com.example.userservice.service;

import com.example.userservice.model.Email;

public interface EmailSender {
    void send(Email email);
}
