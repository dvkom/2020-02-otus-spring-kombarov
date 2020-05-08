package ru.dvkombarov.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dvkombarov.app.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> getById(long id);
}
