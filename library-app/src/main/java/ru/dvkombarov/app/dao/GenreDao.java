package ru.dvkombarov.app.dao;

import ru.dvkombarov.app.domain.Genre;

import java.util.Optional;

public interface GenreDao {
    Optional<Genre> getById(long id);
}
