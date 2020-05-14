package ru.dvkombarov.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Book")
class BookTest {

    @DisplayName("корректно работает конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        String title = "title";
        Author author = new Author(null);
        Genre genre = new Genre(null);
        Book book = new Book(title, author, genre);

        assertThat(book.getTitle()).isEqualTo(title);
        assertThat(book.getAuthor()).isEqualTo(author);
        assertThat(book.getGenre()).isEqualTo(genre);
    }
}