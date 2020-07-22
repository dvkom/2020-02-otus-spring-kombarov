package ru.dvkombarov.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.dvkombarov.app.domain.Book;

import java.util.concurrent.TimeUnit;

@Service
public class BookDesignerService {

    private Logger logger = LoggerFactory.getLogger(BookDesignerService.class);

    public Book design(Book book) throws InterruptedException {
        logger.info("Начало разработки дизайна книги {}", book.getTitle());
        TimeUnit.MILLISECONDS.sleep(150);
        Book result = new Book(book);
        result.setDesigned(true);
        logger.info("Конец разработки дизайна книги {}", book.getTitle());

        return result;
    }
}
