package ru.dvkombarov.app.serivce.businnes;

import org.springframework.stereotype.Service;
import ru.dvkombarov.app.dao.AuthorDao;
import ru.dvkombarov.app.dao.BookDao;
import ru.dvkombarov.app.dao.CommentDao;
import ru.dvkombarov.app.dao.GenreDao;
import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;
import ru.dvkombarov.app.domain.Genre;
import ru.dvkombarov.app.exceptions.DaoOperationException;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final CommentDao commentDao;

    public LibraryServiceImpl(BookDao bookDao, AuthorDao authorDao,
                              GenreDao genreDao, CommentDao commentDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.commentDao = commentDao;
    }

    @Override
    public Book getBookById(long id) {
        return bookDao.getById(id).orElse(null);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    @Override
    public Book saveBook(String title, int pageCount, long authorId, long genreId) {
        Author author = authorDao.getById(authorId)
                .orElseThrow(() -> new DaoOperationException("Ошибка сохранения книги: " +
                        "автор с данным authorId не найден"));
        Genre genre = genreDao.getById(genreId)
                .orElseThrow(() -> new DaoOperationException("Ошибка сохранения книги: " +
                        "жанр с таким genreId не найден"));
        Book book = new Book(0, title, pageCount, author, genre);

        return bookDao.insert(book);
    }

    @Override
    public void deleteBookById(long bookId) {
        bookDao.deleteById(bookId);
    }

    @Override
    public void updateBookInfo(long id, String title, int pageCount, long authorId, long genreId) {
        Book bookToUpdate = bookDao.getById(id)
                .orElseThrow(() -> new DaoOperationException("Ошибка обновления книги: " +
                        "книга с данным id не найдена"));
        Author author = authorDao.getById(authorId)
                .orElseThrow(() -> new DaoOperationException("Ошибка обновления книги: " +
                        "автор с данным authorId не найден"));
        Genre genre = genreDao.getById(genreId)
                .orElseThrow(() -> new DaoOperationException("Ошибка обновления книги: " +
                        "жанр с таким genreId не найден"));

        bookToUpdate.setTitle(title);
        bookToUpdate.setPageCount(pageCount);
        bookToUpdate.setAuthor(author);
        bookToUpdate.setGenre(genre);
        bookDao.update(bookToUpdate);
    }

    @Override
    public Comment saveComment(String text, long bookId) {
        Book book = bookDao.getById(bookId)
                .orElseThrow(() -> new DaoOperationException("Ошибка сохранения комментария: " +
                        "книга с данным id не найдена"));
        Comment comment = new Comment(0, text, book);

        return commentDao.insert(comment);
    }

    @Override
    public Comment getCommentById(long id) {
        return commentDao.getById(id).orElse(null);
    }

    @Override
    public List<Comment> getAllCommentsByBookId(long id) {
        return commentDao.getByBookId(id);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentDao.getAll();
    }
}
