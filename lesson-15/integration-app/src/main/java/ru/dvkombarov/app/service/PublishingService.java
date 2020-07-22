package ru.dvkombarov.app.service;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.PublicationResult;

import java.util.concurrent.TimeUnit;

@Service
public class PublishingService {

    private Logger logger = LoggerFactory.getLogger(PublishingService.class);

    public PublicationResult publish(Book book) throws InterruptedException {
        logger.info("Начало издания книги {}", book);
        TimeUnit.MILLISECONDS.sleep(500);

        PublicationResult result = new PublicationResult(
                book.getTitle(),
                book.getAuthor(),
                PublicationResult.Result.values()[RandomUtils.nextInt(0, 2)]
        );
        logger.info("Издание завершено с результатом {}", result.getResult());

        return result;
    }
}
