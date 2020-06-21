package ru.dvkombarov.app.rest;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;
import ru.dvkombarov.app.domain.Genre;
import ru.dvkombarov.app.repository.BookRepository;
import ru.dvkombarov.app.repository.CommentRepository;
import ru.dvkombarov.app.rest.dto.BookDto;
import ru.dvkombarov.app.rest.dto.CommentDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@DisplayName("Endpoints должны ")
@WebFluxTest
@Import({LibraryEndpointConfiguration.class, LibraryHandler.class})
class LibraryEndpointsTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    @DisplayName("возвращять книгу по id")
    @Test
    void shouldReturnBookById() {
        Book expected = new Book("title", new Author("author"), new Genre("genre"));
        when(bookRepository.findById(anyString())).thenReturn(Mono.just(expected));

        webTestClient
                .get()
                .uri("/api/book/1" )
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(BookDto.class)
                .value(bookDto -> {
                    assertThat(bookDto.getTitle()).isEqualTo(expected.getTitle());
                    assertThat(bookDto.getAuthorName()).isEqualTo(expected.getAuthor().getName());
                    assertThat(bookDto.getGenreName()).isEqualTo(expected.getGenre().getName());
                });
    }

    @DisplayName("возвращять код 400 при неверном id")
    @Test
    void shouldReturnBadRequestWhenBookNotFound() {
        when(bookRepository.findById(anyString())).thenReturn(Mono.empty());

        webTestClient
                .get()
                .uri("/api/book/1" )
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(APPLICATION_JSON);
    }

    @DisplayName("возвращять список всех книг")
    @Test
    void shouldReturnAllBooks() {
        Book expected1 = new Book("title1", new Author("1"), new Genre("1"));
        Book expected2 = new Book("title2", new Author("2"), new Genre("2"));
        when(bookRepository.findAll()).thenReturn(Flux.just(expected1, expected2));

        webTestClient
                .get()
                .uri("/api/books" )
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(List.class)
                .value(list -> assertThat(list).isNotNull().hasSize(2));
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldDeleteBookById() {
        Book book = new Book("title1", new Author("1"), new Genre("1"));
        Comment comment = new Comment("text");
        book.getComments().add(comment);

        when(bookRepository.findById(anyString())).thenReturn(Mono.just(book));
        when(commentRepository.deleteAll(anyList())).thenReturn(Mono.empty());
        when(bookRepository.deleteById(anyString())).thenReturn(Mono.empty());

        webTestClient
                .delete()
                .uri("/api/book/1" )
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(String.class)
                .value(s -> assertThat(s).isNotNull().isEqualTo("1"));
    }

    @DisplayName("добавлять новую книгу")
    @Test
    void shouldAddNewBook() {
        Book expected = new Book("title", new Author("author"), new Genre("genre"));
        BookDto bookDto = new BookDto("0", "title", "author", "genre");
        when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(expected));

        webTestClient
                .post()
                .uri("/api/book" )
                .accept(APPLICATION_JSON)
                .body(Mono.just(bookDto), BookDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(BookDto.class)
                .value(dto -> {
                    assertThat(dto.getTitle()).isEqualTo(expected.getTitle());
                    assertThat(dto.getAuthorName()).isEqualTo(expected.getAuthor().getName());
                    assertThat(dto.getGenreName()).isEqualTo(expected.getGenre().getName());
                });
    }

    @DisplayName("добавлять новый комментарий")
    @Test
    void shouldAddNewComment() {
        CommentDto commentDto = new CommentDto("0", "text", "1");
        Book book = new Book("title1", new Author("1"), new Genre("1"));
        Comment comment = new Comment("text");

        when(bookRepository.findById(anyString())).thenReturn(Mono.just(book));
        when(commentRepository.save(any(Comment.class))).thenReturn(Mono.just(comment));
        when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(book));

        webTestClient
                .post()
                .uri("/api/comment" )
                .accept(APPLICATION_JSON)
                .body(Mono.just(commentDto), CommentDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(CommentDto.class)
                .value(s -> assertThat(s).isNotNull().isEqualTo(commentDto));
    }

    @DisplayName("возвращять комментарии по id книги")
    @Test
    void shouldReturnCommentsByBookId() {
        Book book = new Book("title", new Author("author"), new Genre("genre"));
        Comment comment = new Comment("text");
        book.getComments().add(comment);

        when(bookRepository.findById(anyString())).thenReturn(Mono.just(book));

        webTestClient
                .get()
                .uri("/api/comments/1" )
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(List.class)
                .value(list -> assertThat(list).isNotNull().hasSize(1));
    }
}