package ru.dvkombarov.app.mongock;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.dvkombarov.app.mongock.changelog.DatabaseChangelog;

@Configuration
public class MongockConfig {

    private final MongoClient mongoclient;
    private final String databaseName;

    public MongockConfig(MongoClient mongoclient,
                         @Value("${spring.data.mongodb.database}") String databaseName) {
        this.mongoclient = mongoclient;
        this.databaseName = databaseName;
    }

    @Bean
    public Mongock mongockSpringBoot() {
        return new SpringMongockBuilder(mongoclient, databaseName,
                DatabaseChangelog.class.getPackage().getName())
                .setLockQuickConfig()
                .build();
    }
}
