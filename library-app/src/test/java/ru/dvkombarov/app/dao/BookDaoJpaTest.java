package ru.dvkombarov.app.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Genre;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами должен ")
@DataJpaTest
@Import(BookDaoJpa.class)
public class BookDaoJpaTest {

    @Autowired
    private BookDaoJpa bookDaoJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("загружать информацию о нужной книге по ее id")
    @Test
    void shouldFindExpectedBookById() {
        Optional<Book> optionalActualBook = bookDaoJpa.getById(1L);
        Book expectedBook = em.find(Book.class, 1L);
        assertThat(optionalActualBook).isPresent().get()
                .isEqualToComparingFieldByField(expectedBook);
    }

    @DisplayName("загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectBooksListWithAllInfo() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Book> books = bookDaoJpa.getAll();
        assertThat(books).isNotNull().hasSize(2)
                .allMatch(s -> !s.getTitle().equals(""))
                .allMatch(s -> s.getAuthor() != null)
                .allMatch(s -> s.getGenre() != null)
                .allMatch(s -> s.getComments() != null && s.getComments().size() == 1);
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1);
    }

    @DisplayName("корректно сохранять всю информацию о книге")
    @Test
    void shouldSaveAllBookInfo() {
        Book book = new Book(0, "title", 10, new Author(), new Genre());
        bookDaoJpa.insert(book);
        assertThat(book.getId()).isGreaterThan(0);

        Book actualBook = em.find(Book.class, book.getId());
        assertThat(actualBook).isNotNull()
                .matches(s -> s.getTitle().equals(book.getTitle()))
                .matches(s -> s.getPageCount() == book.getPageCount())
                .matches(s -> s.getAuthor() != null && s.getAuthor().equals(book.getAuthor()))
                .matches(s -> s.getGenre() != null && s.getGenre().equals(book.getGenre()))
                .matches(s -> s.getComments() != null && s.getComments().equals(book.getComments()));
    }

    @DisplayName("обновлять информацию о заданной книге по ее id")
    @Test
    void shouldUpdateBookInfoById() {
        Book firstBook = em.find(Book.class, 1L);
        String oldTitle = firstBook.getTitle();
        int oldPageCount = firstBook.getPageCount();
        Author oldAuthor = firstBook.getAuthor();
        Genre oldGenre = firstBook.getGenre();

        firstBook.setTitle("new title");
        firstBook.setPageCount(2221);
        firstBook.setAuthor(new Author(0, "name", "country", new Date()));
        firstBook.setGenre(new Genre(0, "genre", "descr"));

        bookDaoJpa.update(firstBook);
        Book updatedBook = em.find(Book.class, 1L);

        assertThat(updatedBook.getTitle()).isNotEqualTo(oldTitle).isEqualTo(firstBook.getTitle());
        assertThat(updatedBook.getPageCount()).isNotEqualTo(oldPageCount).isEqualTo(firstBook.getPageCount());
        assertThat(updatedBook.getAuthor()).isNotEqualTo(oldAuthor).isEqualTo(firstBook.getAuthor());
        assertThat(updatedBook.getGenre()).isNotEqualTo(oldGenre).isEqualTo(firstBook.getGenre());
    }

    @DisplayName("удалять заданную книгу по ее id")
    @Test
    void shouldDeleteBookById() {
        Book firstBook = em.find(Book.class, 1L);
        assertThat(firstBook).isNotNull();
        em.detach(firstBook);

        bookDaoJpa.deleteById(1L);
        Book deletedBook = em.find(Book.class, 1L);

        assertThat(deletedBook).isNull();
    }
}
