package ru.dvkombarov.app.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Genre;
import ru.dvkombarov.app.utils.DateUtils;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private static final int EXPECTED_COUNT = 2;
    private final Author author = new Author(3, "name", "country",
            DateUtils.getDateFromString("2020-04-03"));
    private final Genre genre = new Genre(3, "name", "descr");
    private final Book book = new Book(3, "title", 100, author, genre);


    @Autowired
    private BookDaoJdbc dao;

    BookDaoJdbcTest() throws ParseException {
    }

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        int count = dao.getAll().size();
        assertThat(count).isEqualTo(EXPECTED_COUNT);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        dao.insert(book);
        Book actual = dao.getById(book.getId());
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(book);
    }

    @DisplayName("обновлять книгу в БД")
    @Test
    void shouldUpdateBook() throws ParseException {
        Book expected = prepareExistBook("Updated");
        dao.update(expected);
        Book actual = dao.getById(expected.getId());
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @DisplayName("возвращать ожидаемую книгу по id")
    @Test
    void shouldReturnExpectedBookById() throws ParseException {
        Book expected = prepareExistBook("The End of Eternity");
        Book actual = dao.getById(1);
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldDeleteBookById() {
        dao.deleteById(1);
        Book book = dao.getById(1);
        assertThat(book).isNull();
    }

    private Book prepareExistBook(String title) throws ParseException {
        Author author = new Author(1, "Isaac Asimov", "USA",
                DateUtils.getDateFromString("1920-04-06"));
        Genre genre = new Genre(1, "Science fiction", "Description");
        return new Book(1, title, 325, author, genre);
    }
}