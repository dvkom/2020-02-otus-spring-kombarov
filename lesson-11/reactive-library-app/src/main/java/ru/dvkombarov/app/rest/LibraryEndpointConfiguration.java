package ru.dvkombarov.app.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class LibraryEndpointConfiguration {

    @Bean
    RouterFunction<ServerResponse> routes(LibraryHandler handler) {
        return route(GET("/api/book/{id}"), handler::getBook)
                .andRoute(GET("/api/comments/{id}"), handler::getCommentsByBookId)
                .andRoute(GET("/api/books"), handler::getAllBooks)
                .andRoute(DELETE("/api/book/{id}"), handler::deleteBook)
                .andRoute(POST("/api/book"), handler::addBook)
                .andRoute(POST("/api/comment"), handler::addComment);
    }
}
