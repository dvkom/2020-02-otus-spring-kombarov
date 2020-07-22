package ru.dvkombarov.app.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.service.BookDesignerService;
import ru.dvkombarov.app.service.BookEditorService;
import ru.dvkombarov.app.service.PublishingService;

@Configuration
public class IntegrationConfig {

    @Bean
    public QueueChannel bookChannel() {
        return MessageChannels.queue(100).get();
    }

    @Bean
    public DirectChannel resultChannel() {
        return MessageChannels.direct().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).get();
    }

    @Bean
    public IntegrationFlow bookPublishingFlow(PublishingService publishingService,
                                              GenericSelector<Book> noMoreDetectivesFilter,
                                              BookEditorService bookEditorService,
                                              BookDesignerService bookDesignerService) {
        return IntegrationFlows.from("bookChannel")
                .filter(noMoreDetectivesFilter)
                .handle(bookEditorService)
                .handle(bookDesignerService)
                .handle(publishingService)
                .channel("resultChannel")
                .get();
    }
}
