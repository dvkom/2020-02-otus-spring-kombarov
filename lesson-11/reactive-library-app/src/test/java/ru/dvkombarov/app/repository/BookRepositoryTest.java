package ru.dvkombarov.app.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий книги должен ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataMongoTest
class BookRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("корректно соханять книгу")
    @Test
    void shouldSaveCorrect() {
        Book expected = new Book("title", new Author("author"), new Genre("genre"));
        Mono<Book> actual = bookRepository.save(expected);

        StepVerifier
                .create(actual)
                .assertNext(book -> assertThat(book).isEqualToComparingFieldByField(expected))
                .expectComplete()
                .verify();
    }

    @DisplayName("корректно получать книги")
    @Test
    void shouldGetAllCorrect() {
        mongoTemplate.save(
                new Book("title", new Author("author"), new Genre("genre"))
        );

        StepVerifier
                .create(bookRepository.findAll())
                .assertNext(book -> assertThat(book).isNotNull())
                .expectComplete()
                .verify();
    }
}
