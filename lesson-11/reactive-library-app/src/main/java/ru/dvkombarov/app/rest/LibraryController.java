package ru.dvkombarov.app.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.exceptions.DaoOperationException;
import ru.dvkombarov.app.repository.BookRepository;
import ru.dvkombarov.app.repository.CommentRepository;
import ru.dvkombarov.app.rest.dto.BookDto;
import ru.dvkombarov.app.rest.dto.CommentDto;
import ru.dvkombarov.app.rest.dto.ConvertUtils;

import static reactor.core.publisher.Mono.defer;
import static reactor.core.publisher.Mono.error;
import static ru.dvkombarov.app.rest.dto.ConvertUtils.toComment;

// Отключен, работает router. Вариант с AnnotatedController написан в учебных целях
//@RestController
public class LibraryController {

    private final Logger logger = LoggerFactory.getLogger(LibraryController.class);

    private static final String INTERNAL_ERROR_MESSAGE = "При обработке запроса на сервере " +
            "произошла ошибка";
    private static final String BOOK_GET_ERROR = "Ошибка получения книги: " +
            "книга с данным id не найдена";
    private static final String COMMENT_SAVE_ERROR = "Ошибка сохранения комментария: " +
            "книга с данным id не найдена";
    private static final String BOOK_DELETE_ERROR = "Ошибка удаления книги: " +
            "книга с данным id не найдена";
    private static final String COMMENTS_GET_ERROR = "Ошибка получения комментариев: " +
            "книга с данным id не найдена";

    private BookRepository bookRepository;
    private CommentRepository commentRepository;

    public LibraryController(BookRepository bookRepository,
                             CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/api/books")
    public Flux<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .map(ConvertUtils::toBookDto);
    }

    @GetMapping("/api/book/{id}")
    public Mono<BookDto> getBook(@PathVariable("id") String id) {
        return bookRepository.findById(id)
                .map(ConvertUtils::toBookDto)
                .switchIfEmpty(defer(
                        () -> error(new DaoOperationException(BOOK_GET_ERROR))
                ));
    }

    @DeleteMapping("/api/book/{id}")
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
        return bookRepository.findById(id)
                .switchIfEmpty(defer(
                        () -> error(new DaoOperationException(BOOK_DELETE_ERROR))
                ))
                .map(Book::getComments)
                .flatMap(commentRepository::deleteAll)
                .then(bookRepository.deleteById(id));
    }

    @PostMapping("/api/book")
    public Mono<BookDto> addBook(@RequestBody BookDto bookDto) {
        return bookRepository.save(ConvertUtils.toBook(bookDto))
                .map(ConvertUtils::toBookDto);
    }

    @PostMapping("/api/comment")
    public Mono<CommentDto> addComment(@RequestBody CommentDto commentDto) {
        return bookRepository.findById(commentDto.getBookId())
                .switchIfEmpty(defer(
                        () -> error(new DaoOperationException(COMMENT_SAVE_ERROR))
                ))
                .zipWith(commentRepository.save(toComment(commentDto)), (book, comment) -> {
                    book.getComments().add(comment);
                    return book;
                })
                .flatMap(bookRepository::save)
                .then(defer(() -> Mono.just(commentDto)));
    }

    @GetMapping("/api/comments/{id}")
    public Flux<CommentDto> getCommentsByBookId(@PathVariable("id") String id) {
        return bookRepository.findById(id)
                .switchIfEmpty(defer(
                        () -> error(new DaoOperationException(COMMENTS_GET_ERROR))
                ))
                .map(Book::getComments)
                .flatMapMany(Flux::fromIterable)
                .map(comment -> ConvertUtils.toCommentDto(id, comment));
    }
}
