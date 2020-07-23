package ru.dvkombarov.app.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;
import ru.dvkombarov.app.domain.Genre;
import ru.dvkombarov.app.domain.migration.MongoAuthor;
import ru.dvkombarov.app.domain.migration.MongoBook;
import ru.dvkombarov.app.domain.migration.MongoComment;
import ru.dvkombarov.app.domain.migration.MongoGenre;
import ru.dvkombarov.app.repository.MongoAuthorRepository;
import ru.dvkombarov.app.repository.MongoGenreRepository;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
public class BooksMigrationStepConfig {

    private static final String BOOKS_QUERY = "select b from Book b join fetch b.comments";

    private StepBuilderFactory stepBuilderFactory;
    private EntityManagerFactory entityManagerFactory;
    private MongoOperations mongoOperations;
    private AppProps appProps;
    private MongoAuthorRepository mongoAuthorRepository;
    private MongoGenreRepository mongoGenreRepository;

    public BooksMigrationStepConfig(StepBuilderFactory stepBuilderFactory,
                                    EntityManagerFactory entityManagerFactory,
                                    MongoOperations mongoOperations,
                                    AppProps appProps,
                                    MongoAuthorRepository mongoAuthorRepository,
                                    MongoGenreRepository mongoGenreRepository) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
        this.mongoOperations = mongoOperations;
        this.appProps = appProps;
        this.mongoAuthorRepository = mongoAuthorRepository;
        this.mongoGenreRepository = mongoGenreRepository;
    }

    @StepScope
    @Bean
    public JpaPagingItemReader<Book> bookReader() {
        return new JpaPagingItemReaderBuilder<Book>()
                .name("bookReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString(BOOKS_QUERY)
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Book, MongoBook> bookProcessor() {
        return this::process;
    }

    private MongoBook process(Book book) {
        MongoAuthor mongoAuthor = Optional.ofNullable(book.getAuthor())
                .map(Author::getName)
                .map(mongoAuthorRepository::getByName)
                .orElseThrow(
                        () -> new RuntimeException("Не найден автор для книги " + book.getTitle())
                );

        MongoGenre mongoGenre = Optional.ofNullable(book.getGenre())
                .map(Genre::getName)
                .map(mongoGenreRepository::getByName)
                .orElseThrow(
                        () -> new RuntimeException("Не найден жанр для книги " + book.getTitle())
                );

        MongoBook mongoBook = new MongoBook(book.getTitle(), mongoAuthor, mongoGenre);
        List<MongoComment> mongoComments = book.getComments().stream()
                .map(Comment::getText)
                .map(MongoComment::new)
                .collect(Collectors.toList());
        mongoBook.getComments().addAll(mongoComments);

        return mongoBook;
    }

    @StepScope
    @Bean
    public MongoItemWriter<MongoBook> bookWriter() {
        return new MongoItemWriterBuilder<MongoBook>()
                .template(mongoOperations)
                .collection("books")
                .build();
    }

    @Bean
    public Step booksMigration() {
        return stepBuilderFactory.get("booksMigration")
                .<Book, MongoBook>chunk(appProps.getChunkSize())
                .reader(bookReader())
                .processor(bookProcessor())
                .writer(bookWriter())
                .build();
    }
}
