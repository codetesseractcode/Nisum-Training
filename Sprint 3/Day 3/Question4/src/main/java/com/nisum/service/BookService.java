package com.nisum.service;

import com.nisum.model.Book;
import com.nisum.exception.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final Map<Long, Book> bookRepository = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // Add a few sample books for testing
    public BookService() {
        Book book1 = new Book(idGenerator.getAndIncrement(), "The Great Gatsby", "F. Scott Fitzgerald", 1925);
        Book book2 = new Book(idGenerator.getAndIncrement(), "To Kill a Mockingbird", "Harper Lee", 1960);
        Book book3 = new Book(idGenerator.getAndIncrement(), "1984", "George Orwell", 1949);
        Book book4 = new Book(idGenerator.getAndIncrement(), "Pride and Prejudice", "Jane Austen", 1813);
        Book book5 = new Book(idGenerator.getAndIncrement(), "The Catcher in the Rye", "J.D. Salinger", 1951);

        bookRepository.put(book1.getId(), book1);
        bookRepository.put(book2.getId(), book2);
        bookRepository.put(book3.getId(), book3);
        bookRepository.put(book4.getId(), book4);
        bookRepository.put(book5.getId(), book5);
    }

    public List<Book> getAllBooks(int page, int size, String author, Integer publishedYear) {
        List<Book> filteredBooks = bookRepository.values().stream()
                .filter(book -> author == null || book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .filter(book -> publishedYear == null || book.getPublishedYear().equals(publishedYear))
                .collect(Collectors.toList());

        // Apply pagination
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, filteredBooks.size());

        if (startIndex >= filteredBooks.size()) {
            return new ArrayList<>();
        }

        return filteredBooks.subList(startIndex, endIndex);
    }

    public int getTotalBooks(String author, Integer publishedYear) {
        return (int) bookRepository.values().stream()
                .filter(book -> author == null || book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .filter(book -> publishedYear == null || book.getPublishedYear().equals(publishedYear))
                .count();
    }

    public Book getBookById(Long id) {
        Book book = bookRepository.get(id);
        if (book == null) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        return book;
    }

    public Book addBook(Book book) {
        book.setId(idGenerator.getAndIncrement());
        bookRepository.put(book.getId(), book);
        return book;
    }

    public Book updateBook(Long id, Book bookDetails) {
        if (!bookRepository.containsKey(id)) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }

        bookDetails.setId(id);
        bookRepository.put(id, bookDetails);
        return bookDetails;
    }

    public void deleteBook(Long id) {
        if (!bookRepository.containsKey(id)) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        bookRepository.remove(id);
    }
}
