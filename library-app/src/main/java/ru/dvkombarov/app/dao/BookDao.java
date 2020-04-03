package ru.dvkombarov.app.dao;

import ru.dvkombarov.app.domain.Book;

import java.util.List;

public interface BookDao {

    void insert(Book book);
    void update(Book book);
    Book getById(long id);
    void deleteById(long id);
    List<Book> getAll();
}
