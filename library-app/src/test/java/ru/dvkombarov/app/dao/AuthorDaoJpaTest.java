package ru.dvkombarov.app.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.dvkombarov.app.domain.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с авторами должен ")
@DataJpaTest
@Import(AuthorDaoJpa.class)
public class AuthorDaoJpaTest {

    @Autowired
    private AuthorDaoJpa authorDaoJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужном авторе по его id")
    @Test
    void shouldFindExpectedAuthorById() {
        Optional<Author> optionalActualAuthor = authorDaoJpa.getById(1L);
        Author expectedAuthor = em.find(Author.class, 1L);
        assertThat(optionalActualAuthor).isPresent().get()
                .isEqualToComparingFieldByField(expectedAuthor);
    }
}
