package ru.dvkombarov.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.dvkombarov.app.domain.Book;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {

    Optional<Book> getById(String id);
    Book getByTitle(String title);
}
