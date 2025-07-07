package com.nisum.bookapi.service.impl;

import com.nisum.bookapi.dto.BookCreateRequest;
import com.nisum.bookapi.dto.BookResponse;
import com.nisum.bookapi.dto.BookUpdateRequest;
import com.nisum.bookapi.entity.Book;
import com.nisum.bookapi.exception.BookNotFoundException;
import com.nisum.bookapi.exception.DuplicateIsbnException;
import com.nisum.bookapi.repository.BookRepository;
import com.nisum.bookapi.service.BookService;
import com.nisum.bookapi.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookResponse createBook(BookCreateRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateIsbnException("Book with ISBN " + request.getIsbn() + " already exists");
        }

        Book book = bookMapper.toEntity(request);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponse(savedBook);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        return bookMapper.toResponse(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
            .stream()
            .map(bookMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public BookResponse updateBook(Long id, BookUpdateRequest request) {
        Book existingBook = bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        // Check for ISBN uniqueness if ISBN is being updated
        if (request.getIsbn() != null && !request.getIsbn().equals(existingBook.getIsbn())) {
            if (bookRepository.existsByIsbnAndIdNot(request.getIsbn(), id)) {
                throw new DuplicateIsbnException("Book with ISBN " + request.getIsbn() + " already exists");
            }
        }

        bookMapper.updateEntityFromRequest(request, existingBook);
        Book updatedBook = bookRepository.save(existingBook);
        return bookMapper.toResponse(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}
