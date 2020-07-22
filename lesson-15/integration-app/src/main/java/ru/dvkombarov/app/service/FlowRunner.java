package ru.dvkombarov.app.service;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.stereotype.Component;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.integration.PublishingOfficeGateway;
import ru.dvkombarov.app.repository.BookRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class FlowRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(FlowRunner.class);

    private BookRepository bookRepository;
    private PublishingOfficeGateway publishingOfficeGateway;
    private DirectChannel resultChannel;
    private QueueChannel bookChannel;

    public FlowRunner(BookRepository bookRepository,
                      PublishingOfficeGateway publishingOfficeGateway,
                      DirectChannel resultChannel,
                      QueueChannel bookChannel) {
        this.bookRepository = bookRepository;
        this.publishingOfficeGateway = publishingOfficeGateway;
        this.resultChannel = resultChannel;
        this.bookChannel = bookChannel;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run(ApplicationArguments args) throws InterruptedException {
        resultChannel.subscribe(message ->
                logger.info("Конец flow издания книги, результат = {} ", message.getPayload())
        );
        List<Book> books = bookRepository.findAll();

        while (true) {
            Book bookToPublish = books.get(RandomUtils.nextInt(0, books.size()));
            logger.info("Книга для издания: {}", bookToPublish);
            publishingOfficeGateway.process(bookToPublish);
            logger.info("Размер очереди на издание: {}", bookChannel.getQueueSize());
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
