package ru.dvkombarov.app.rest;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.exceptions.DaoOperationException;
import ru.dvkombarov.app.repository.BookRepository;
import ru.dvkombarov.app.repository.CommentRepository;
import ru.dvkombarov.app.rest.dto.BookDto;
import ru.dvkombarov.app.rest.dto.CommentDto;
import ru.dvkombarov.app.rest.dto.ConvertUtils;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.defer;
import static reactor.core.publisher.Mono.error;
import static ru.dvkombarov.app.rest.dto.ConvertUtils.toComment;
import static ru.dvkombarov.app.rest.dto.ConvertUtils.toCommentDto;

@Service
public class LibraryHandler {

    private static final String BOOK_GET_ERROR = "Ошибка получения книги: " +
            "книга с данным id не найдена";
    private static final String COMMENT_SAVE_ERROR = "Ошибка сохранения комментария: " +
            "книга с данным id не найдена";
    private static final String BOOK_DELETE_ERROR = "Ошибка удаления книги: " +
            "книга с данным id не найдена";
    private static final String COMMENTS_GET_ERROR = "Ошибка получения комментариев: " +
            "книга с данным id не найдена";
    private static final String INTERNAL_ERROR_MESSAGE = "При обработке запроса на сервере " +
            "произошла ошибка";

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    public LibraryHandler(BookRepository bookRepository,
                          CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @Nonnull
    Mono<ServerResponse> getBook(ServerRequest r) {
        return bookRepository.findById(id(r))
                .map(ConvertUtils::toBookDto)
                .switchIfEmpty(defer(
                        () -> error(new DaoOperationException(BOOK_GET_ERROR))
                ))
                .flatMap(LibraryHandler::buildResponse)
                .onErrorResume(LibraryHandler::getResponseByException);
    }

    @Nonnull
    Mono<ServerResponse> getAllBooks(ServerRequest r) {
        return bookRepository.findAll()
                .map(ConvertUtils::toBookDto)
                .collect(Collectors.toList())
                .flatMap(LibraryHandler::buildResponse)
                .onErrorResume(LibraryHandler::getResponseByException);

    }

    @Nonnull
    Mono<ServerResponse> deleteBook(ServerRequest r) {
        String id = id(r);
        return bookRepository.findById(id)
                .switchIfEmpty(defer(
                        () -> error(new DaoOperationException(BOOK_DELETE_ERROR))
                ))
                .map(Book::getComments)
                .flatMap(commentRepository::deleteAll)
                .then(bookRepository.deleteById(id))
                .then(defer(() -> Mono.just(id)))
                .flatMap(LibraryHandler::buildResponse)
                .onErrorResume(LibraryHandler::getResponseByException);
    }

    @Nonnull
    Mono<ServerResponse> addBook(ServerRequest r) {
        return r.bodyToMono(BookDto.class)
                .map(ConvertUtils::toBook)
                .flatMap(bookRepository::save)
                .map(ConvertUtils::toBookDto)
                .flatMap(LibraryHandler::buildResponse)
                .onErrorResume(LibraryHandler::getResponseByException);
    }

    @Nonnull
    Mono<ServerResponse> addComment(ServerRequest r) {
        return r.bodyToMono(CommentDto.class)
                .flatMap(commentDto -> bookRepository.findById(commentDto.getBookId())
                        .switchIfEmpty(defer(
                                () -> error(new DaoOperationException(COMMENT_SAVE_ERROR))
                        ))
                        .zipWith(commentRepository.save(toComment(commentDto)), (book, comment) -> {
                            book.getComments().add(comment);
                            return book;
                        })
                        .flatMap(bookRepository::save)
                        .then(defer(() -> Mono.just(commentDto)))
                )
                .flatMap(LibraryHandler::buildResponse)
                .onErrorResume(LibraryHandler::getResponseByException);
    }

    @Nonnull
    Mono<ServerResponse> getCommentsByBookId(ServerRequest r) {
        String id = id(r);
        return bookRepository.findById(id)
                .switchIfEmpty(defer(
                        () -> error(new DaoOperationException(COMMENTS_GET_ERROR))
                ))
                .map(Book::getComments)
                .flatMapMany(Flux::fromIterable)
                .map(comment -> toCommentDto(id, comment))
                .collect(Collectors.toList())
                .flatMap(LibraryHandler::buildResponse)
                .onErrorResume(LibraryHandler::getResponseByException);
    }

    private static Mono<ServerResponse> buildResponse(Object dto) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(dto);
    }

    private static String id(ServerRequest r) {
        return r.pathVariable("id");
    }

    private static Mono<ServerResponse> getResponseByException(Throwable throwable) {
        return throwable instanceof DaoOperationException ?
                Mono.just(throwable.getMessage())
                        .flatMap(s -> ServerResponse.badRequest()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(s)) :
                Mono.just(INTERNAL_ERROR_MESSAGE)
                        .flatMap(s -> ServerResponse.status(INTERNAL_SERVER_ERROR)
                                .contentType(APPLICATION_JSON)
                                .bodyValue(s));
    }
}
