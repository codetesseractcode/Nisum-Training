package com.nisum.libraryapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.libraryapi.entity.Book;
import com.nisum.libraryapi.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@Transactional
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Book testBook1;
    private Book testBook2;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();

        testBook1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925);
        testBook2 = new Book("To Kill a Mockingbird", "Harper Lee", 1960);

        bookRepository.save(testBook1);
        bookRepository.save(testBook2);
    }

    @Test
    void getAllBooks_ShouldReturnAllBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("The Great Gatsby")))
                .andExpect(jsonPath("$[0].author", is("F. Scott Fitzgerald")))
                .andExpect(jsonPath("$[0].publishedYear", is(1925)))
                .andExpect(jsonPath("$[1].title", is("To Kill a Mockingbird")))
                .andExpect(jsonPath("$[1].author", is("Harper Lee")))
                .andExpect(jsonPath("$[1].publishedYear", is(1960)));
    }

    @Test
    void getBookById_WhenBookExists_ShouldReturnBook() throws Exception {
        Long bookId = testBook1.getId();

        mockMvc.perform(get("/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(bookId.intValue())))
                .andExpect(jsonPath("$.title", is("The Great Gatsby")))
                .andExpect(jsonPath("$.author", is("F. Scott Fitzgerald")))
                .andExpect(jsonPath("$.publishedYear", is(1925)));
    }

    @Test
    void getBookById_WhenBookDoesNotExist_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/books/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void createBook_WithValidData_ShouldCreateBook() throws Exception {
        Book newBook = new Book("1984", "George Orwell", 1949);
        String bookJson = objectMapper.writeValueAsString(newBook);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is("1984")))
                .andExpect(jsonPath("$.author", is("George Orwell")))
                .andExpect(jsonPath("$.publishedYear", is(1949)))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    void createBook_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        Book invalidBook = new Book("", "", null); // Invalid data
        String bookJson = objectMapper.writeValueAsString(invalidBook);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Validation failed")))
                .andExpect(jsonPath("$.errors.title", containsString("required")))
                .andExpect(jsonPath("$.errors.author", containsString("required")))
                .andExpect(jsonPath("$.errors.publishedYear", containsString("required")));
    }

    @Test
    void updateBook_WhenBookExists_ShouldUpdateBook() throws Exception {
        Long bookId = testBook1.getId();
        Book updatedBook = new Book("The Great Gatsby - Updated", "F. Scott Fitzgerald", 1926);
        String bookJson = objectMapper.writeValueAsString(updatedBook);

        mockMvc.perform(put("/books/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(bookId.intValue())))
                .andExpect(jsonPath("$.title", is("The Great Gatsby - Updated")))
                .andExpect(jsonPath("$.author", is("F. Scott Fitzgerald")))
                .andExpect(jsonPath("$.publishedYear", is(1926)));
    }

    @Test
    void updateBook_WhenBookDoesNotExist_ShouldReturnNotFound() throws Exception {
        Book updatedBook = new Book("Non-existent Book", "Unknown Author", 2023);
        String bookJson = objectMapper.writeValueAsString(updatedBook);

        mockMvc.perform(put("/books/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", containsString("Book not found with id: 999")));
    }

    @Test
    void updateBook_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        Long bookId = testBook1.getId();
        Book invalidBook = new Book("", "", null);
        String bookJson = objectMapper.writeValueAsString(invalidBook);

        mockMvc.perform(put("/books/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Validation failed")));
    }

    @Test
    void deleteBook_WhenBookExists_ShouldDeleteBook() throws Exception {
        Long bookId = testBook1.getId();

        mockMvc.perform(delete("/books/{id}", bookId))
                .andExpect(status().isNoContent());

        // Verify book was deleted
        mockMvc.perform(get("/books/{id}", bookId))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBook_WhenBookDoesNotExist_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(delete("/books/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", containsString("Book not found with id: 999")));
    }

    @Test
    void searchBooks_ByAuthor_ShouldReturnMatchingBooks() throws Exception {
        mockMvc.perform(get("/books/search")
                .param("author", "Harper Lee"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("To Kill a Mockingbird")))
                .andExpect(jsonPath("$[0].author", is("Harper Lee")));
    }

    @Test
    void searchBooks_ByTitle_ShouldReturnMatchingBooks() throws Exception {
        mockMvc.perform(get("/books/search")
                .param("title", "gatsby"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("The Great Gatsby")));
    }

    @Test
    void searchBooks_WithoutParameters_ShouldReturnAllBooks() throws Exception {
        mockMvc.perform(get("/books/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
