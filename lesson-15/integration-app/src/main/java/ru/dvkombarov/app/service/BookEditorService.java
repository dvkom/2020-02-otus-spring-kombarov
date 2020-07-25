package ru.dvkombarov.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.dvkombarov.app.domain.Book;

import java.util.concurrent.TimeUnit;

@Service
public class BookEditorService {

    private Logger logger = LoggerFactory.getLogger(BookEditorService.class);

    public Book edit(Book book) throws InterruptedException {
        logger.info("Начало редактирования книги {}", book.getTitle());
        TimeUnit.MILLISECONDS.sleep(150);
        Book result = new Book(book);
        result.setEdited(true);
        logger.info("Конец редактирования книги {}", book.getTitle());

        return result;
    }
}
