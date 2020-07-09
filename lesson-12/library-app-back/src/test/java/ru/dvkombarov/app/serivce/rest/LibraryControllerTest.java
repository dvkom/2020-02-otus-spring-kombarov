package ru.dvkombarov.app.serivce.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;
import ru.dvkombarov.app.domain.Genre;
import ru.dvkombarov.app.serivce.businnes.LibraryService;
import ru.dvkombarov.app.serivce.rest.dto.BookFullInfoDto;
import ru.dvkombarov.app.serivce.rest.dto.CommentDto;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryController.class)
@WithMockUser(
        username = "Bob",
        password = "password1"
)
@DisplayName("Методы контроллера библиотеки должны ")
class LibraryControllerTest {

    private static final Book book = new Book(1, "", 12,
            new Author(0, "", ""),
            new Genre(0, "")
    );

    @MockBean
    private LibraryService libraryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("возвращать успешный ответ при запросе превью книг")
    void shouldReturnSuccessAnswer_whenGetAllBookPreviewsCall() throws Exception {
        doReturn(List.of(book))
                .when(libraryService).getAllBooks();
        mockMvc.perform(get("/api/books/preview"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("возвращать успешный ответ при запросе полной информаии о книге по Id")
    void shouldReturnSuccessAnswer_whenGetBookFullInfoCall() throws Exception {
        doReturn(book)
                .when(libraryService).getBookById(anyLong());
        mockMvc.perform(get("/api/book/1"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("возвращать успешный ответ при обновлении книги")
    void shouldReturnSuccessAnswer_whenUpdateBookCall() throws Exception {
        BookFullInfoDto bookFullInfoDto = new BookFullInfoDto(
                0, "", 1, "", "", ""
        );
        doReturn(book).when(libraryService)
                .updateBook(anyLong(), anyString(), anyInt(), anyString(), anyString(), anyString());
        mockMvc.perform(put("/api/book")
                .content(asJsonString(bookFullInfoDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("возвращать успешный ответ при добавлении книги")
    void shouldReturnSuccessAnswer_whenAddBookCall() throws Exception {
        BookFullInfoDto bookFullInfoDto = new BookFullInfoDto(
                0, "", 1, "", "", ""
        );
        doReturn(book).when(libraryService)
                .saveBook(anyString(), anyInt(), anyString(), anyString(), anyString());
        mockMvc.perform(post("/api/book")
                .content(asJsonString(bookFullInfoDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("возвращать успешный ответ при добавлении книги")
    void shouldReturnSuccessAnswer_whenDeleteBookCall() throws Exception {
        doReturn(book).when(libraryService)
                .saveBook(anyString(), anyInt(), anyString(), anyString(), anyString());
        mockMvc.perform(delete("/api/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("возвращать успешный ответ при запросе комментариев по Id книги")
    void shouldReturnSuccessAnswer_whenGetCommentsByBookIdCall() throws Exception {
        List<Comment> comments = List.of(new Comment(0, "", book));
        doReturn(comments)
                .when(libraryService).getAllCommentsByBookId(anyLong());
        mockMvc.perform(get("/api/comments/1"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("возвращать успешный ответ при добавлении комментария")
    void shouldReturnSuccessAnswer_whenAddCommentCall() throws Exception {
        Comment comment = new Comment(1, "", book);
        CommentDto commentDto = new CommentDto(0, "", 1);
        doReturn(comment).when(libraryService)
                .saveComment(anyString(),anyLong());
        mockMvc.perform(post("/api/comment")
                .content(asJsonString(commentDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("возвращать успешный ответ при вызове аутентификации")
    void shouldReturnSuccessAnswer_whenAuthCall() throws Exception {
        mockMvc.perform(get("/api/auth")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}