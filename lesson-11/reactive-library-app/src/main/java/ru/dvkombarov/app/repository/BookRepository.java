package ru.dvkombarov.app.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.dvkombarov.app.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
