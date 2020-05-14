package ru.dvkombarov.app.serivce.businnes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;
import ru.dvkombarov.app.domain.Genre;
import ru.dvkombarov.app.repository.BookRepository;
import ru.dvkombarov.app.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("Методы сервиса библиотеки должны ")
class LibraryServiceImplTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private LibraryService libraryService;

    @Test
    @DisplayName("возвращять книгу по Id")
    void shouldReturnBookById() {
        Book book = new Book("title", new Author(), new Genre());
        book.setId("1");
        doReturn(Optional.of(book)).when(bookRepository)
                .getById(anyString());

        assertThat(libraryService.getBookById("1")).isEqualTo(book);
    }

    @Test
    @DisplayName("возвращять все книги")
    void shouldReturnAllBooks() {
        List<Book> books = List.of(new Book("title", new Author(), new Genre()));
        doReturn(books).when(bookRepository)
                .findAll();

        assertThat(libraryService.getAllBooks()).isEqualTo(books);
    }

    @Test
    @DisplayName("сохранять новую книгу")
    void shouldSaveNewBook() {
        Book book = new Book("title", new Author("author"), new Genre("genre"));
        libraryService.saveBook("title", "author", "genre");

        verify(bookRepository).save(eq(book));
    }

    @Test
    @DisplayName("обновлять существующую книгу")
    void shouldUpdateExistingBook() {
        Book oldBook = new Book("title_2", new Author("author"), new Genre("genre"));
        Book expectedBook = new Book(
                "title_2", new Author("author2"), new Genre("genre2")
        );
        doReturn(oldBook).when(bookRepository).getByTitle("title_2");
        libraryService.saveBook("title_2", "author2", "genre2");

        verify(bookRepository).save(eq(expectedBook));
    }

    @Test
    @DisplayName("вызывать метод delete для id")
    void shouldCallDeleteBookMethod() {
        String id = "10";
        Book book = new Book();
        Comment comment = new Comment();
        comment.setId(id);
        book.setComments(List.of(comment));
        doReturn(Optional.of(book)).when(bookRepository).getById(anyString());
        libraryService.deleteBookById(id);

        verify(bookRepository).deleteById(id);
        verify(commentRepository).deleteAll(List.of(comment));
    }

    @Test
    @DisplayName("вызывать метод insert для комментария")
    void shouldCallSaveCommentMethod() {
        Comment comment = new Comment("comment");
        Book book = new Book("title", new Author(), new Genre());
        book.getComments().add(comment);
        doReturn(Optional.of(book)).when(bookRepository).getById(eq("1"));
        libraryService.saveComment("comment", "1");

        verify(commentRepository).save(eq(comment));
        verify(bookRepository).save(eq(book));
    }

    @Test
    @DisplayName("возвращять комментарий по Id")
    void shouldReturnCommentById() {
        Comment comment = new Comment("comment");
        doReturn(Optional.of(comment)).when(commentRepository).getById(anyString());

        assertThat(libraryService.getCommentById("1")).isEqualTo(comment);
    }

    @Test
    @DisplayName("возвращять все комментарии по Id книги")
    void shouldReturnAllCommentsByBookId() {
        List<Comment> comments = List.of(new Comment("comment"));
        Book book = new Book();
        book.setComments(comments);
        doReturn(Optional.of(book)).when(bookRepository).getById(anyString());

        assertThat(libraryService.getAllCommentsByBookId("1")).isEqualTo(comments);
    }

    @Test
    @DisplayName("возвращять все комментарии")
    void shouldReturnAllComments() {
        List<Comment> comments = List.of(new Comment("comment"));
        doReturn(comments).when(commentRepository).findAll();

        assertThat(libraryService.getAllComments()).isEqualTo(comments);
    }
}