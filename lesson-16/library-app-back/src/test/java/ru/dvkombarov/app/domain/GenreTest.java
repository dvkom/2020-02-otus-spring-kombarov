package ru.dvkombarov.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Genre")
class GenreTest {

    @DisplayName("корректно работает конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        long id = 1;
        String name = "name";
        Genre genre = new Genre(id, name);

        assertThat(genre.getId()).isEqualTo(id);
        assertThat(genre.getName()).isEqualTo(name);
    }
}