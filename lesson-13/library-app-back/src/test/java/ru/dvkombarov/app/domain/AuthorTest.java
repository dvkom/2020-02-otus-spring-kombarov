package ru.dvkombarov.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Author")
class AuthorTest {

    @DisplayName("корректно работает конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        long id = 1;
        String name = "name";
        String country = "country";
        Author author = new Author(id, name, country);

        assertThat(author.getId()).isEqualTo(id);
        assertThat(author.getName()).isEqualTo(name);
        assertThat(author.getCountry()).isEqualTo(country);
    }
}