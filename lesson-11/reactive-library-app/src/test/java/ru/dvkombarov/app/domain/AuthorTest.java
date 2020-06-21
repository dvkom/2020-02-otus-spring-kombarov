package ru.dvkombarov.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Author")
class AuthorTest {

    @DisplayName("корректно работает конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        String name = "name";
        Author author = new Author(name);

        assertThat(author.getName()).isEqualTo(name);
    }
}