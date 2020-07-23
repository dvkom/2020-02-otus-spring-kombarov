package ru.dvkombarov.app.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.dvkombarov.app.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "with-author-and-genre-eg", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Book> getById(long id);

    @EntityGraph(value = "with-author-and-genre-eg", type = EntityGraph.EntityGraphType.LOAD)
    List<Book> findAll();
}
