package ru.dvkombarov.app.serivce.businnes;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;
import ru.dvkombarov.app.domain.Genre;
import ru.dvkombarov.app.exceptions.DaoOperationException;
import ru.dvkombarov.app.repository.BookRepository;
import ru.dvkombarov.app.repository.CommentRepository;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;


    public LibraryServiceImpl(BookRepository bookRepository,
                              CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Book getBookById(String id) {
        return bookRepository.getById(id).orElse(null);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book saveBook(String title, String authorName, String genreName) {
        Book book = bookRepository.getByTitle(title);
        Author author = new Author(authorName);
        Genre genre = new Genre(genreName);

        if (book == null) {
            book = new Book(title, author, genre);
        } else {
            book.setAuthor(author);
            book.setGenre(genre);
        }

        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(String bookId) {
        Book book = bookRepository.getById(bookId)
                .orElseThrow(() -> new DaoOperationException("Ошибка удаления книги: " +
                        "книга с данным id не найдена"));

        if (!CollectionUtils.isEmpty(book.getComments())) {
            commentRepository.deleteAll(book.getComments());
        }

        bookRepository.deleteById(bookId);
    }

    @Override
    public Comment saveComment(String text, String bookId) {
        Book book = bookRepository.getById(bookId)
                .orElseThrow(() -> new DaoOperationException("Ошибка сохранения комментария: " +
                        "книга с данным id не найдена"));
        Comment comment = commentRepository.save(new Comment(text));
        book.getComments().add(comment);
        bookRepository.save(book);

        return comment;
    }

    @Override
    public Comment getCommentById(String id) {
        return commentRepository.getById(id).orElse(null);
    }

    @Override
    public List<Comment> getAllCommentsByBookId(String id) {
        Book book = bookRepository.getById(id)
                .orElseThrow(() -> new DaoOperationException("Ошибка получения комментариев: " +
                        "книга с данным id не найдена"));

        return book.getComments();
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
