package ru.dvkombarov.app.serivce.businnes;

import org.springframework.stereotype.Service;
import ru.dvkombarov.app.dao.BookDao;
import ru.dvkombarov.app.domain.Book;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookDao bookDao;

    public LibraryServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book getBookById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    @Override
    public void saveBook(Book book) {
        bookDao.insert(book);
    }

    @Override
    public void deleteBookById(long bookId) {
        bookDao.deleteById(bookId);
    }

    @Override
    public void updateBookInfo(Book book) {
        bookDao.update(book);
    }
}
