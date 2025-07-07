package com.nisum.bookapi.service;

import com.nisum.bookapi.dto.BookCreateRequest;
import com.nisum.bookapi.dto.BookResponse;
import com.nisum.bookapi.dto.BookUpdateRequest;
import java.util.List;

public interface BookService {

    BookResponse createBook(BookCreateRequest request);

    BookResponse getBookById(Long id);

    List<BookResponse> getAllBooks();

    BookResponse updateBook(Long id, BookUpdateRequest request);

    void deleteBook(Long id);
}
