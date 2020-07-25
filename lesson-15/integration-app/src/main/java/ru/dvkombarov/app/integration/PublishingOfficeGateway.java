package ru.dvkombarov.app.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Payload;
import ru.dvkombarov.app.domain.Book;

@MessagingGateway
public interface PublishingOfficeGateway {

    @Gateway(requestChannel = "bookChannel")
    void process(@Payload Book book);
}
