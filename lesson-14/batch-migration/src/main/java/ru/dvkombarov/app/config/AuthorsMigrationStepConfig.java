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
import ru.dvkombarov.app.domain.migration.MongoAuthor;

import javax.persistence.EntityManagerFactory;

@Configuration
public class AuthorsMigrationStepConfig {

    private static final String AUTHORS_QUERY = "select a from Author a";

    private StepBuilderFactory stepBuilderFactory;
    private EntityManagerFactory entityManagerFactory;
    private MongoOperations mongoOperations;
    private AppProps appProps;

    public AuthorsMigrationStepConfig(StepBuilderFactory stepBuilderFactory,
                                      EntityManagerFactory entityManagerFactory,
                                      MongoOperations mongoOperations,
                                      AppProps appProps) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
        this.mongoOperations = mongoOperations;
        this.appProps = appProps;
    }

    @StepScope
    @Bean
    public JpaPagingItemReader<Author> authorReader() {
        return new JpaPagingItemReaderBuilder<Author>()
                .name("authorReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString(AUTHORS_QUERY)
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Author, MongoAuthor> authorProcessor() {
        return this::process;
    }

    private MongoAuthor process(Author author) {
        return new MongoAuthor(
                author.getName(),
                author.getCountry()
        );
    }

    @StepScope
    @Bean
    public MongoItemWriter<MongoAuthor> authorWriter() {
        return new MongoItemWriterBuilder<MongoAuthor>()
                .template(mongoOperations)
                .collection("authors")
                .build();
    }

    @Bean
    public Step authorsMigration() {
        return stepBuilderFactory.get("authorsMigration")
                .<Author, MongoAuthor>chunk(appProps.getChunkSize())
                .reader(authorReader())
                .processor(authorProcessor())
                .writer(authorWriter())
                .build();
    }
}
