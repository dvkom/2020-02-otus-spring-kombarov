package ru.dvkombarov.app.serivce.businnes;

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
    public Book getBookById(long id) {
        return bookRepository.getById(id)
                .orElseThrow(() -> new DaoOperationException("Книга с данным id не найдена"));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteBookById(long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
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
    public Comment saveComment(String text, long bookId) {
        Book book = bookRepository.getById(bookId)
                .orElseThrow(() -> new DaoOperationException("Ошибка сохранения комментария: " +
                        "книга с данным id не найдена"));
        Comment comment = new Comment(0, text, book);

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllCommentsByBookId(long id) {
        return commentRepository.getByBookId(id);
    }
}
