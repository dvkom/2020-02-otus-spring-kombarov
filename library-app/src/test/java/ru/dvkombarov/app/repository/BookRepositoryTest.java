package ru.dvkombarov.app.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами должен ")
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("загружать информацию о нужной книге по ее id")
    @Test
    void shouldFindExpectedBookById() {
        Optional<Book> optionalActualBook = bookRepository.getById(1L);
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
        sessionFactory.getStatistics().clear();

        List<Book> books = bookRepository.findAll();
        assertThat(books).isNotNull().hasSize(2)
                .allMatch(s -> !s.getTitle().equals(""))
                .allMatch(s -> s.getAuthor() != null)
                .allMatch(s -> s.getGenre() != null);
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1);
    }

    @DisplayName("корректно сохранять всю информацию о книге")
    @Test
    void shouldSaveAllBookInfo() {
        Book book = new Book(0, "title", 10, new Author(), new Genre());
        bookRepository.save(book);
        assertThat(book.getId()).isGreaterThan(0);

        Book actualBook = em.find(Book.class, book.getId());
        assertThat(actualBook).isNotNull()
                .matches(s -> s.getTitle().equals(book.getTitle()))
                .matches(s -> s.getPageCount() == book.getPageCount())
                .matches(s -> s.getAuthor() != null && s.getAuthor().equals(book.getAuthor()))
                .matches(s -> s.getGenre() != null && s.getGenre().equals(book.getGenre()));
    }

    @DisplayName("удалять заданную книгу по ее id")
    @Test
    void shouldDeleteBookById() {
        Book firstBook = em.find(Book.class, 1L);
        assertThat(firstBook).isNotNull();
        em.detach(firstBook);

        bookRepository.deleteById(1L);
        Book deletedBook = em.find(Book.class, 1L);

        assertThat(deletedBook).isNull();
    }
}
