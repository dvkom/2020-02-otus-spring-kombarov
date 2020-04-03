package ru.dvkombarov.app.serivce.businnes;

import ru.dvkombarov.app.domain.Book;

import java.util.List;

public interface LibraryService {
    Book getBookById(long id);
    List<Book> getAllBooks();
    void saveBook(Book book);
    void deleteBookById(long bookId);
    void updateBookInfo(Book book);
}
