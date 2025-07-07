package com.nisum.bookapi.mapper;

import com.nisum.bookapi.dto.BookCreateRequest;
import com.nisum.bookapi.dto.BookResponse;
import com.nisum.bookapi.dto.BookUpdateRequest;
import com.nisum.bookapi.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toEntity(BookCreateRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublicationYear(request.getPublicationYear());
        book.setPrice(request.getPrice());
        return book;
    }

    public BookResponse toResponse(Book book) {
        return new BookResponse(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getIsbn(),
            book.getPublicationYear(),
            book.getPrice(),
            book.getCreatedAt(),
            book.getUpdatedAt()
        );
    }

    public void updateEntityFromRequest(BookUpdateRequest request, Book book) {
        if (request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            book.setAuthor(request.getAuthor());
        }
        if (request.getIsbn() != null) {
            book.setIsbn(request.getIsbn());
        }
        if (request.getPublicationYear() != null) {
            book.setPublicationYear(request.getPublicationYear());
        }
        if (request.getPrice() != null) {
            book.setPrice(request.getPrice());
        }
    }
}
