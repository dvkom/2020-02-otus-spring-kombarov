package ru.dvkombarov.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Author")
class AuthorTest {

    @DisplayName("корректно работает конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        long id = 1;
        String name = "name";
        String country = "country";
        Date birthDate = new Date();
        Author author = new Author(id, name, country, birthDate);

        assertThat(author.getId()).isEqualTo(id);
        assertThat(author.getName()).isEqualTo(name);
        assertThat(author.getCountry()).isEqualTo(country);
        assertThat(author.getBirthDate()).isEqualTo(birthDate);
    }
}