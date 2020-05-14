package ru.dvkombarov.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Genre")
class GenreTest {

    @DisplayName("корректно работает конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        String name = "name";
        Genre genre = new Genre(name);

        assertThat(genre.getName()).isEqualTo(name);
    }
}