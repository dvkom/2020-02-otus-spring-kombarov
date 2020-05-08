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
import ru.dvkombarov.app.repository.AuthorRepository;
import ru.dvkombarov.app.repository.BookRepository;
import ru.dvkombarov.app.repository.CommentRepository;
import ru.dvkombarov.app.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("Методы сервиса библиотеки должны ")
class LibraryServiceImplTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private LibraryService libraryService;

    @Test
    @DisplayName("возвращять книгу по Id")
    void shouldReturnBookById() {
        Book book = new Book(1, null, 1, null, null);
        doReturn(Optional.of(book)).when(bookRepository)
                .getById(anyLong());
        assertThat(libraryService.getBookById(1)).isEqualTo(book);
    }

    @Test
    @DisplayName("возвращять все книги")
    void shouldReturnAllBooks() {
        List<Book> books = List.of(new Book(1, null, 1, null, null));
        doReturn(books).when(bookRepository)
                .findAll();
        assertThat(libraryService.getAllBooks()).isEqualTo(books);
    }

    @Test
    @DisplayName("вызывать метод insert для книги")
    void shouldCallSaveBookMethod() {
        Author author = new Author(1, null, null, null);
        Genre genre = new Genre(1, null, null);
        Book book = new Book(0, null, 1, author, genre);
        doReturn(Optional.of(author)).when(authorRepository).getById(anyLong());
        doReturn(Optional.of(genre)).when(genreRepository).getById(anyLong());

        libraryService.saveBook(null, 1, 1, 1);
        verify(bookRepository).save(eq(book));
    }

    @Test
    @DisplayName("вызывать метод delete для id")
    void shouldCallDeleteBookMethod() {
        long id = 10;
        libraryService.deleteBookById(id);
        verify(bookRepository).deleteById(id);
    }

    @Test
    @DisplayName("вызывать метод update для книги")
    void shouldCallUpdateBookMethod() {
        Author author = new Author(1, null, null, null);
        Genre genre = new Genre(1, null, null);
        Book book = new Book(0, null, 1, author, genre);
        doReturn(Optional.of(author)).when(authorRepository).getById(anyLong());
        doReturn(Optional.of(genre)).when(genreRepository).getById(anyLong());
        doReturn(Optional.of(book)).when(bookRepository).getById(anyLong());

        libraryService.updateBookInfo(1, null, 1, 1, 1);
        verify(bookRepository).save(eq(book));
    }

    @Test
    @DisplayName("вызывать метод insert для комментария")
    void shouldCallSaveCommentMethod() {
        Author author = new Author(1, null, null, null);
        Genre genre = new Genre(1, null, null);
        Book book = new Book(0, null, 1, author, genre);
        Comment comment = new Comment(0, null, book);
        doReturn(Optional.of(book)).when(bookRepository).getById(anyLong());

        libraryService.saveComment(null, 0);
        verify(commentRepository).save(eq(comment));
    }

    @Test
    @DisplayName("возвращять комментарий по Id")
    void shouldReturnCommentById() {
        Comment comment = new Comment(0, null, new Book());
        doReturn(Optional.of(comment)).when(commentRepository)
                .getById(anyLong());
        assertThat(libraryService.getCommentById(0)).isEqualTo(comment);
    }

    @Test
    @DisplayName("возвращять все комментарии по Id книги")
    void shouldReturnAllCommentsByBookId() {
        List<Comment> comments = List.of(new Comment(0, null, new Book()));

        doReturn(comments).when(commentRepository)
                .getByBookId(anyLong());
        assertThat(libraryService.getAllCommentsByBookId(1)).isEqualTo(comments);
    }

    @Test
    @DisplayName("возвращять все комментарии")
    void shouldReturnAllComments() {
        List<Comment> comments = List.of(new Comment(0, null, new Book()));
        doReturn(comments).when(commentRepository)
                .findAll();
        assertThat(libraryService.getAllComments()).isEqualTo(comments);
    }
}