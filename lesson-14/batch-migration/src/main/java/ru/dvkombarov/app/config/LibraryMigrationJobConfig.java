package ru.dvkombarov.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibraryMigrationJobConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String LIBRARY_MIGRATION_JOB = "libraryMigrationJob";

    private JobBuilderFactory jobBuilderFactory;
    private  Step authorsMigration;
    private  Step genresMigration;
    private  Step booksMigration;

    public LibraryMigrationJobConfig(JobBuilderFactory jobBuilderFactory,
                                     Step authorsMigration,
                                     Step genresMigration,
                                     Step booksMigration) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.authorsMigration = authorsMigration;
        this.genresMigration = genresMigration;
        this.booksMigration = booksMigration;
    }

    @Bean
    public Job libraryMigrationJob() {
        return jobBuilderFactory.get(LIBRARY_MIGRATION_JOB)
                .incrementer(new RunIdIncrementer())
                .start(authorsMigration)
                .next(genresMigration)
                .next(booksMigration)
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
