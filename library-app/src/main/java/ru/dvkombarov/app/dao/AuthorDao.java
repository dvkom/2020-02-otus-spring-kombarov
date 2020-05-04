package ru.dvkombarov.app.dao;

import ru.dvkombarov.app.domain.Author;

import java.util.Optional;

public interface AuthorDao {
    Optional<Author> getById(long id);
}
