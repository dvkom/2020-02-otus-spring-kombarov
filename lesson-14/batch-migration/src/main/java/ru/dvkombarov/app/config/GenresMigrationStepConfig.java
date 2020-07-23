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
import ru.dvkombarov.app.domain.Genre;
import ru.dvkombarov.app.domain.migration.MongoGenre;

import javax.persistence.EntityManagerFactory;

@Configuration
public class GenresMigrationStepConfig {

    private static final String GENRES_QUERY = "select g from Genre g";

    private StepBuilderFactory stepBuilderFactory;
    private EntityManagerFactory entityManagerFactory;
    private MongoOperations mongoOperations;
    private AppProps appProps;

    public GenresMigrationStepConfig(StepBuilderFactory stepBuilderFactory,
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
    public JpaPagingItemReader<Genre> genreReader() {
        return new JpaPagingItemReaderBuilder<Genre>()
                .name("genreReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString(GENRES_QUERY)
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Genre, MongoGenre> genreProcessor() {
        return this::process;
    }

    private MongoGenre process(Genre genre) {
        return new MongoGenre(genre.getName());
    }

    @StepScope
    @Bean
    public MongoItemWriter<MongoGenre> genreWriter() {
        return new MongoItemWriterBuilder<MongoGenre>()
                .template(mongoOperations)
                .collection("genres")
                .build();
    }

    @Bean
    public Step genresMigration() {
        return stepBuilderFactory.get("genresMigration")
                .<Genre, MongoGenre>chunk(appProps.getChunkSize())
                .reader(genreReader())
                .processor(genreProcessor())
                .writer(genreWriter())
                .build();
    }
}
