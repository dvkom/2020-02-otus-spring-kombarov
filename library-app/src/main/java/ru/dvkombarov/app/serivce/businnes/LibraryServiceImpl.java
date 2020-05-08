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
        return bookRepository.getById(id).orElse(null);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book saveBook(String title, int pageCount, long authorId, long genreId) {
        Author author = authorRepository.getById(authorId)
                .orElseThrow(() -> new DaoOperationException("Ошибка сохранения книги: " +
                        "автор с данным authorId не найден"));
        Genre genre = genreRepository.getById(genreId)
                .orElseThrow(() -> new DaoOperationException("Ошибка сохранения книги: " +
                        "жанр с таким genreId не найден"));
        Book book = new Book(0, title, pageCount, author, genre);

        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public void updateBookInfo(long id, String title, int pageCount, long authorId, long genreId) {
        Book bookToUpdate = bookRepository.getById(id)
                .orElseThrow(() -> new DaoOperationException("Ошибка обновления книги: " +
                        "книга с данным id не найдена"));
        Author author = authorRepository.getById(authorId)
                .orElseThrow(() -> new DaoOperationException("Ошибка обновления книги: " +
                        "автор с данным authorId не найден"));
        Genre genre = genreRepository.getById(genreId)
                .orElseThrow(() -> new DaoOperationException("Ошибка обновления книги: " +
                        "жанр с таким genreId не найден"));

        bookToUpdate.setTitle(title);
        bookToUpdate.setPageCount(pageCount);
        bookToUpdate.setAuthor(author);
        bookToUpdate.setGenre(genre);
        bookRepository.save(bookToUpdate);
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
    public Comment getCommentById(long id) {
        return commentRepository.getById(id).orElse(null);
    }

    @Override
    public List<Comment> getAllCommentsByBookId(long id) {
        return commentRepository.getByBookId(id);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
