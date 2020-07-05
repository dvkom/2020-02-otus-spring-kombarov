package ru.dvkombarov.app.serivce.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;
import ru.dvkombarov.app.exceptions.DaoOperationException;
import ru.dvkombarov.app.serivce.businnes.LibraryService;
import ru.dvkombarov.app.serivce.rest.dto.BookFullInfoDto;
import ru.dvkombarov.app.serivce.rest.dto.BookPreviewDto;
import ru.dvkombarov.app.serivce.rest.dto.CommentDto;
import ru.dvkombarov.app.serivce.rest.dto.ConvertUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
public class LibraryController {

    private final Logger logger = LoggerFactory.getLogger(LibraryController.class);

    private static final String INTERNAL_ERROR_MESSAGE = "При обработке запроса на сервере " +
            "произошла ошибка";
    private LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/api/books/preview")
    public List<BookPreviewDto> getAllBookPreviews() {
        return libraryService.getAllBooks().stream()
                .map(ConvertUtils::toBookPreviewDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/book/{id}")
    public BookFullInfoDto getBookFullInfo(@PathVariable("id") long id) {
        return ConvertUtils.toBookFullInfoDto(libraryService.getBookById(id));
    }

    @PutMapping("/api/book")
    public BookFullInfoDto updateBook(@RequestBody BookFullInfoDto bookFullInfoDto) {
        Book updatedBook = libraryService.updateBook(
                bookFullInfoDto.getId(),
                bookFullInfoDto.getTitle(),
                bookFullInfoDto.getPageCount(),
                bookFullInfoDto.getAuthorName(),
                bookFullInfoDto.getAuthorCountry(),
                bookFullInfoDto.getGenreName()
        );

        return ConvertUtils.toBookFullInfoDto(updatedBook);
    }

    @PostMapping("/api/book")
    public BookFullInfoDto addBook(@RequestBody BookFullInfoDto bookFullInfoDto) {
        Book savedBook = libraryService.saveBook(
                bookFullInfoDto.getTitle(),
                bookFullInfoDto.getPageCount(),
                bookFullInfoDto.getAuthorName(),
                bookFullInfoDto.getAuthorCountry(),
                bookFullInfoDto.getGenreName()
        );

        return ConvertUtils.toBookFullInfoDto(savedBook);
    }

    @DeleteMapping("/api/book/{id}")
    public long deleteBook(@PathVariable("id") long id) {
        libraryService.deleteBookById(id);

        return id;
    }

    @GetMapping("/api/comments/{id}")
    public List<CommentDto> getCommentsByBookId(@PathVariable("id") long id) {
        return libraryService.getAllCommentsByBookId(id)
                .stream()
                .map(ConvertUtils::toCommentDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/comment")
    public CommentDto addComment(@RequestBody CommentDto commentDto) {
        Comment savedComment = libraryService.saveComment(
                commentDto.getText(),
                commentDto.getBookId()
        );

        return ConvertUtils.toCommentDto(savedComment);
    }

    @GetMapping("/api/auth")
    public void getAuth() {

    }

    @ExceptionHandler(DaoOperationException.class)
    public ResponseEntity<String> handleDaoOperationException(DaoOperationException e) {
        logger.error(e.getMessage(), e);

        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAnotherException(Exception e) {
        logger.error(INTERNAL_ERROR_MESSAGE, e);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(INTERNAL_ERROR_MESSAGE);
    }
}
