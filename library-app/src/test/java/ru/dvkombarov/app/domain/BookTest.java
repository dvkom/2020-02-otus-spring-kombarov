package ru.dvkombarov.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Book")
class BookTest {

    @DisplayName("корректно работает конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        long id = 1;
        String title = "title";
        int pageCount = 200;
        Author author = new Author(1, null, null, null);
        Genre genre = new Genre(1, null, null);
        Book book = new Book(id, title, pageCount, author, genre);

        assertThat(book.getId()).isEqualTo(id);
        assertThat(book.getTitle()).isEqualTo(title);
        assertThat(book.getPageCount()).isEqualTo(pageCount);
        assertThat(book.getAuthor()).isEqualTo(author);
        assertThat(book.getGenre()).isEqualTo(genre);
    }
}