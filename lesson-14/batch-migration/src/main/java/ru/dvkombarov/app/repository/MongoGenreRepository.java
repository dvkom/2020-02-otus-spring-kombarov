package ru.dvkombarov.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.dvkombarov.app.domain.migration.MongoGenre;

public interface MongoGenreRepository extends MongoRepository<MongoGenre, String> {
    MongoGenre getByName(String name);
}
