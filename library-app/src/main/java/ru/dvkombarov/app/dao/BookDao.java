package ru.dvkombarov.app.dao;

import ru.dvkombarov.app.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book insert(Book book);
    void update(Book book);
    Optional<Book> getById(long id);
    void deleteById(long id);
    List<Book> getAll();
}
