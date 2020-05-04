package ru.dvkombarov.app.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.dvkombarov.app.domain.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами должен ")
@DataJpaTest
@Import(GenreDaoJpa.class)
public class GenreDaoJpaTest {

    @Autowired
    private GenreDaoJpa genreDaoJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужном жанре по его id")
    @Test
    void shouldFindExpectedGenreById() {
        Optional<Genre> optionalActualGenre = genreDaoJpa.getById(1L);
        Genre expectedGenre = em.find(Genre.class, 1L);
        assertThat(optionalActualGenre).isPresent().get()
                .isEqualToComparingFieldByField(expectedGenre);
    }
}
