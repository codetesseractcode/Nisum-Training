package com.example.bookapi.controller;

import com.example.bookapi.exception.BookNotFoundException;
import com.example.bookapi.model.Book;
import com.example.bookapi.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureRestDocs
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    void getBookById_ValidId_Returns200WithJsonBody() throws Exception {
        // Given
        Long bookId = 1L;
        Book book = new Book(bookId, "The Great Gatsby", "F. Scott Fitzgerald", "978-0-7432-7356-5");
        when(bookService.findById(bookId)).thenReturn(book);

        // When & Then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/books/{id}", bookId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(bookId))
                .andExpect(jsonPath("$.title").value("The Great Gatsby"))
                .andExpect(jsonPath("$.author").value("F. Scott Fitzgerald"))
                .andExpect(jsonPath("$.isbn").value("978-0-7432-7356-5"))
                .andDo(document("get-book-success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                            parameterWithName("id").description("The ID of the book to retrieve")
                        ),
                        responseFields(
                            fieldWithPath("id").description("The unique identifier of the book"),
                            fieldWithPath("title").description("The title of the book"),
                            fieldWithPath("author").description("The author of the book"),
                            fieldWithPath("isbn").description("The ISBN of the book")
                        )
                ));
    }

    @Test
    void getBookById_InvalidId_Returns404WithProblemDetails() throws Exception {
        // Given
        Long nonExistentId = 999L;
        when(bookService.findById(nonExistentId)).thenThrow(new BookNotFoundException(nonExistentId));

        // When & Then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/books/{id}", nonExistentId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/problem+json"))
                .andExpect(jsonPath("$.type").exists())
                .andExpect(jsonPath("$.title").value("Book Not Found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("Book not found with id: " + nonExistentId))
                .andDo(document("get-book-not-found",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                            parameterWithName("id").description("The ID of the book that was not found")
                        ),
                        responseFields(
                            fieldWithPath("type").description("A URI reference that identifies the problem type"),
                            fieldWithPath("title").description("A short, human-readable summary of the problem"),
                            fieldWithPath("status").description("The HTTP status code"),
                            fieldWithPath("detail").description("A human-readable explanation specific to this occurrence"),
                            fieldWithPath("instance").description("A URI reference that identifies the specific occurrence")
                        )
                ));
    }
}
