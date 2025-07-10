package com.example.bookapi.service;

import com.example.bookapi.exception.BookNotFoundException;
import com.example.bookapi.model.Book;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    private final Map<Long, Book> books = new HashMap<>();

    public BookServiceImpl() {
        // Initialize with some sample data
        books.put(1L, new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald", "978-0-7432-7356-5"));
        books.put(2L, new Book(2L, "To Kill a Mockingbird", "Harper Lee", "978-0-06-112008-4"));
        books.put(3L, new Book(3L, "1984", "George Orwell", "978-0-452-28423-4"));
    }

    @Override
    public Book findById(Long id) {
        Book book = books.get(id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        return book;
    }
}
