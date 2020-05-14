package ru.dvkombarov.app.serivce.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;
import ru.dvkombarov.app.exceptions.DaoOperationException;
import ru.dvkombarov.app.serivce.businnes.LibraryService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
public class ApplicationCommands {

    private static final String DONE_MESSAGE = "Сделано!";
    private static final String ERROR_MESSAGE = "Произошла ошибка...";
    private static final String NOT_FOUND_MESSAGE = "Не найдено";
    private final LibraryService libraryService;

    public ApplicationCommands(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @ShellMethod(value = "Get all books", key = {"get-books", "gbs"})
    public String getAllBooks() {
        String result;
        try {
            result = Optional.ofNullable(libraryService.getAllBooks())
                    .stream()
                    .flatMap(Collection::stream)
                    .map(Book::toString)
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            e.printStackTrace();
            result = ERROR_MESSAGE;
        }

        return result;
    }

    @ShellMethod(value = "Get a book by Id", key = {"get-book", "gb"})
    public String getBookById(String id) {
        return Optional.ofNullable(libraryService.getBookById(id))
                .map(Book::toString)
                .orElse(NOT_FOUND_MESSAGE);
    }

    @ShellMethod(value = "Delete a book by Id", key = {"delete-book", "db"})
    public String deleteBookById(String id) {
        String result = DONE_MESSAGE;
        try {
            libraryService.deleteBookById(id);
        } catch (DaoOperationException daoException) {
            result = daoException.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            result = ERROR_MESSAGE;
        }

        return result;
    }

    @ShellMethod(value = "Save a book (add new or update exist by title)", key = {"save-book", "sb"})
    public String saveBook(String title, String authorName, String genreName) {
        String result = DONE_MESSAGE;
        try {
            libraryService.saveBook(title, authorName, genreName);
        } catch (Exception e) {
            e.printStackTrace();
            result = ERROR_MESSAGE;
        }

        return result;
    }

    @ShellMethod(value = "Get all comments", key = {"get-cmts", "gcs"})
    public String getAllComments() {
        String result;
        try {
            result = Optional.ofNullable(libraryService.getAllComments())
                    .stream()
                    .flatMap(Collection::stream)
                    .map(Comment::toString)
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            e.printStackTrace();
            result = ERROR_MESSAGE;
        }

        return result;
    }

    @ShellMethod(value = "Get a comment by Id", key = {"get-cmt", "gc"})
    public String getCommentById(String id) {
        return Optional.ofNullable(libraryService.getCommentById(id))
                .map(Comment::toString)
                .orElse(NOT_FOUND_MESSAGE);
    }

    @ShellMethod(value = "Get all comments by book Id", key = {"get-cmts-by", "gcsb"})
    public String getAllCommentsForBook(String id) {
        String result;
        try {
            result = Optional.ofNullable(libraryService.getAllCommentsByBookId(id))
                    .stream()
                    .flatMap(Collection::stream)
                    .map(Comment::toString)
                    .collect(Collectors.joining("\n"));
        } catch (DaoOperationException daoException) {
            result = daoException.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            result = ERROR_MESSAGE;
        }

        return result;
    }

    @ShellMethod(value = "Add new comment", key = {"add-cmt", "ac"})
    public String addComment(String text, String bookId) {
        String result = DONE_MESSAGE;
        try {
            libraryService.saveComment(text, bookId);
        } catch (DaoOperationException daoException) {
            result = daoException.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            result = ERROR_MESSAGE;
        }

        return result;
    }
}