package ru.dvkombarov.app.monitoring;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.dvkombarov.app.repository.BookRepository;

@Component
public class BookCountHealthIndicator implements HealthIndicator {

    static final String COUNT = "Count";
    private static final long MAX_COUNT = 1200L;
    private BookRepository bookRepository;

    public BookCountHealthIndicator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Health health() {
        try {
            long bookCount = bookRepository.count();
            if (bookCount > 0 && bookCount <= MAX_COUNT) {
                return Health.up().withDetail(COUNT, bookCount).build();
            }

            return Health.down().withDetail(COUNT, bookCount).build();
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }
}
