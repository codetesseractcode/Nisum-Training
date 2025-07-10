package com.example.bookapi.service;

import com.example.bookapi.model.Book;

public interface BookService {
    Book findById(Long id);
}
