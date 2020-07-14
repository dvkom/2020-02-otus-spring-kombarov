package ru.dvkombarov.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;
import ru.dvkombarov.app.domain.migration.MongoAuthor;
import ru.dvkombarov.app.domain.migration.MongoBook;
import ru.dvkombarov.app.domain.migration.MongoComment;
import ru.dvkombarov.app.domain.migration.MongoGenre;

import javax.persistence.EntityManagerFactory;

@Configuration
public class JobConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final int CHUNK_SIZE = 10;
    private static final String LIBRARY_MIGRATION_JOB = "libraryMigrationJob";
    private static final String BOOKS_QUERY = "select b from Book b join fetch " +
            "b.author join fetch b.genre";
    private static final String COMMENTS_QUERY = "select c from Comment c join fetch c.book";

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private EntityManagerFactory entityManagerFactory;
    private MongoOperations mongoOperations;

    public JobConfig(JobBuilderFactory jobBuilderFactory,
                     StepBuilderFactory stepBuilderFactory,
                     EntityManagerFactory entityManagerFactory,
                     MongoOperations mongoOperations) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
        this.mongoOperations = mongoOperations;
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

    private MongoBook process(Book item) {
        return new MongoBook(
                item.getTitle(),
                new MongoAuthor(item.getAuthor().getName()),
                new MongoGenre(item.getGenre().getName())
        );
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
                .<Book, MongoBook>chunk(CHUNK_SIZE)
                .reader(bookReader())
                .processor(bookProcessor())
                .writer(bookWriter())
                .build();
    }

    @StepScope
    @Bean
    public JpaPagingItemReader<Comment> commentReader() {
        return new JpaPagingItemReaderBuilder<Comment>()
                .name("CommentReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString(COMMENTS_QUERY)
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Comment, MongoComment> commentProcessor() {
        return this::process;
    }

    private MongoComment process(Comment item) {
        return new MongoComment(
                item.getText()
        );
    }

    @StepScope
    @Bean
    public MongoItemWriter<MongoComment> commentWriter() {
        return new MongoItemWriterBuilder<MongoComment>()
                .template(mongoOperations)
                .collection("comments")
                .build();
    }

    @Bean
    public Step commentsMigration() {
        return stepBuilderFactory.get("commentsMigration")
                .<Comment, MongoComment>chunk(CHUNK_SIZE)
                .reader(commentReader())
                .processor(commentProcessor())
                .writer(commentWriter())
                .build();
    }


    @Bean
    public Job libraryMigrationJob() {
        return jobBuilderFactory.get(LIBRARY_MIGRATION_JOB)
                .incrementer(new RunIdIncrementer())
                .start(commentsMigration())
                .next(booksMigration())
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }
}
