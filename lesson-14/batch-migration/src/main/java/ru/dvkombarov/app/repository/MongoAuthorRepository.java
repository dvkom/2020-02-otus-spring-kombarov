package ru.dvkombarov.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.dvkombarov.app.domain.migration.MongoAuthor;

public interface MongoAuthorRepository extends MongoRepository<MongoAuthor, String> {
    MongoAuthor getByName(String name);
}
