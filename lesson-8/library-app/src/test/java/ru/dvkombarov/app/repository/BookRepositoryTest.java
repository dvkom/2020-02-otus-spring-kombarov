package ru.dvkombarov.app.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class BookRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void getByIdTest() {
        Book expected = mongoTemplate.save(
                new Book("title", new Author("author"), new Genre("genre"))
        );
        Book actual = bookRepository.getById(expected.getId()).orElse(new Book());

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @Test
    void getByTitleTest() {
        Book expected = mongoTemplate.save(
                new Book("title2", new Author("author2"), new Genre("genre2"))
        );
        Book actual = bookRepository.getByTitle("title2");

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }
}
