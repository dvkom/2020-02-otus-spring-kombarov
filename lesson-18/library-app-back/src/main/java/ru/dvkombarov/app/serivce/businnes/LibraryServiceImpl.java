package ru.dvkombarov.app.serivce.businnes;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;
import ru.dvkombarov.app.domain.Genre;
import ru.dvkombarov.app.exceptions.DaoOperationException;
import ru.dvkombarov.app.repository.AuthorRepository;
import ru.dvkombarov.app.repository.BookRepository;
import ru.dvkombarov.app.repository.CommentRepository;
import ru.dvkombarov.app.repository.GenreRepository;

import java.util.List;

import static ru.dvkombarov.app.security.Roles.*;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;


    public LibraryServiceImpl(BookRepository bookRepository,
                              AuthorRepository authorRepository,
                              GenreRepository genreRepository,
                              CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @HystrixCommand(commandKey = "getBookByIdKey", fallbackMethod = "getFallbackBook")
    @Secured({ROLE_USER, ROLE_EDITOR, ROLE_USER_READ_ONLY})
    public Book getBookById(long id) {
        return bookRepository.getById(id)
                .orElseThrow(() -> new DaoOperationException("Книга с данным id не найдена"));
    }

    @Override
    @HystrixCommand(commandKey = "getAllBooksKey", fallbackMethod = "getFallbackBooks")
    @Secured({ROLE_USER, ROLE_EDITOR, ROLE_USER_READ_ONLY})
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @HystrixCommand(commandKey = "deleteBookByIdKey")
    @Secured(ROLE_EDITOR)
    public void deleteBookById(long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    @HystrixCommand(commandKey = "updateBookKey", fallbackMethod = "getFallbackUpdatedBook")
    @Secured(ROLE_EDITOR)
    public Book updateBook(long id, String title, int pageCount, String authorName,
                           String authorCountry, String genreName) {
        Book bookToUpdate = bookRepository.getById(id)
                .orElseThrow(() -> new DaoOperationException("Ошибка обновления книги: " +
                        "книга с данным id не найдена"));
        Author author = bookToUpdate.getAuthor();
        Genre genre = bookToUpdate.getGenre();

        if (!author.getName().equals(authorName) || !author.getCountry().equals(authorCountry)) {
            author = authorRepository.getByNameAndCountry(authorName, authorCountry)
                    .orElseGet(() ->
                            authorRepository.save(new Author(0, authorName, authorCountry)));
        }

        if (!genre.getName().equals(genreName)) {
            genre = genreRepository.getByName(genreName)
                    .orElseGet(() -> genreRepository.save(new Genre(0, genreName)));
        }

        bookToUpdate.setTitle(title);
        bookToUpdate.setPageCount(pageCount);
        bookToUpdate.setAuthor(author);
        bookToUpdate.setGenre(genre);

        return bookRepository.save(bookToUpdate);
    }

    @Override
    @HystrixCommand(commandKey = "saveBookKey", fallbackMethod = "getFallbackSavedBook")
    @Secured(ROLE_EDITOR)
    public Book saveBook(String title, int pageCount, String authorName,
                         String authorCountry, String genreName) {

        Author author = authorRepository.getByNameAndCountry(authorName, authorCountry)
                .orElseGet(() ->
                        authorRepository.save(new Author(0, authorName, authorCountry)));

        Genre genre = genreRepository.getByName(genreName)
                .orElseGet(() -> genreRepository.save(new Genre(0, genreName)));

        Book book = new Book(0, title, pageCount, author, genre);

        return bookRepository.save(book);
    }

    @Override
    @HystrixCommand(commandKey = "saveCommentKey", fallbackMethod = "getFallbackSavedComment")
    @Secured({ROLE_USER, ROLE_EDITOR})
    public Comment saveComment(String text, long bookId) {
        Book book = bookRepository.getById(bookId)
                .orElseThrow(() -> new DaoOperationException("Ошибка сохранения комментария: " +
                        "книга с данным id не найдена"));
        Comment comment = new Comment(0, text, book);

        return commentRepository.save(comment);
    }

    @Override
    @HystrixCommand(commandKey = "getAllCommentsByBookIdKey", fallbackMethod = "getFallbackComments")
    @Secured({ROLE_USER, ROLE_EDITOR, ROLE_USER_READ_ONLY})
    public List<Comment> getAllCommentsByBookId(long id) {
        return commentRepository.getByBookId(id);
    }

    private Book getFallbackBook(long id) {
        return new Book(
                id, "n/a", 0,
                new Author(0L, "n/a", "n/a"),
                new Genre(0L, "n/a")
        );
    }

    private List<Book> getFallbackBooks() {
        return List.of(getFallbackBook(0L));
    }

    private Book getFallbackUpdatedBook(long id, String title, int pageCount, String authorName,
                                       String authorCountry, String genreName) {
        return getFallbackBook(0L);
    }

    private Book getFallbackSavedBook(String title, int pageCount, String authorName,
                                     String authorCountry, String genreName) {
        return getFallbackBook(0L);
    }

    private Comment getFallbackSavedComment(String text, long bookId) {
        return new Comment(0L, "n/a", getFallbackBook(0L));
    }

    private List<Comment> getFallbackComments(long id) {
        return List.of(new Comment(0L, "n/a", getFallbackBook(0L)));
    }
}
