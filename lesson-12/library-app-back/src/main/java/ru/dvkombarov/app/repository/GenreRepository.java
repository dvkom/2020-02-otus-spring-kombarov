package ru.dvkombarov.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dvkombarov.app.domain.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> getByName(String name);
}
