package ru.dvkombarov.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.dvkombarov.app.domain.migration.MongoBook;

import java.util.Optional;

public interface BookRepository extends MongoRepository<MongoBook, String> {

    Optional<MongoBook> getById(String id);
    Optional<MongoBook> getByTitle(String title);
}
