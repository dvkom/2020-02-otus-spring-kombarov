package ru.dvkombarov.app.monitoring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.dvkombarov.app.repository.BookRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static ru.dvkombarov.app.monitoring.BookCountHealthIndicator.COUNT;

@SpringBootTest
@DisplayName("Методы health-indicator должны ")
class BookCountHealthIndicatorTest {

    @Autowired
    private BookCountHealthIndicator bookCountHealthIndicator;

    @MockBean
    private BookRepository bookRepository;

    @Test
    @DisplayName("возвращять статус UP когда в библиотеке есть книги")
    void shouldReturnUp_whenBooksExist() {
        doReturn(2L).when(bookRepository).count();

        Health result = bookCountHealthIndicator.health();
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(Status.UP);
        assertThat(result.getDetails().get(COUNT)).isEqualTo(2L);
    }

    @Test
    @DisplayName("возвращять статус DOWN когда в библиотеке нет книг")
    void shouldReturnDown_whenBooksNotExist() {
        doReturn(0L).when(bookRepository).count();

        Health result = bookCountHealthIndicator.health();
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(Status.DOWN);
        assertThat(result.getDetails().get(COUNT)).isEqualTo(0L);
    }

    @Test
    @DisplayName("возвращять статус DOWN при ошибке")
    void shouldReturnDown_whenExceptionHappened() {
        doThrow(new RuntimeException()).when(bookRepository).count();

        Health result = bookCountHealthIndicator.health();
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(Status.DOWN);
        assertThat(result.getDetails().get("error")).isNotNull();
    }
}