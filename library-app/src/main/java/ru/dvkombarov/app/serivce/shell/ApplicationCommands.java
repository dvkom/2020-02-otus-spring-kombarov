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
    public String getBookById(long id) {
        return Optional.ofNullable(libraryService.getBookById(id))
                .map(Book::toString)
                .orElse(NOT_FOUND_MESSAGE);
    }

    @ShellMethod(value = "Delete a book by Id", key = {"delete-book", "db"})
    public String deleteBookById(long id) {
        String result = DONE_MESSAGE;
        try {
            libraryService.deleteBookById(id);
        } catch (Exception e) {
            result = ERROR_MESSAGE;
        }

        return result;
    }

    @ShellMethod(value = "Add new book", key = {"add-book", "ab"})
    public String addBook(String title, int pageCount, long authorId, long genreId) {
        String result = DONE_MESSAGE;
        try {
            libraryService.saveBook(title, pageCount, authorId, genreId);
        } catch (DaoOperationException daoException) {
            result = daoException.getMessage();
        } catch (Exception e) {
            result = ERROR_MESSAGE;
        }

        return result;
    }

    @ShellMethod(value = "Update a book info", key = {"update-book", "ub"})
    public String updateBookInfo(long id, String title, int pageCount, long authorId, long genreId) {
        String result = DONE_MESSAGE;
        try {
            libraryService.updateBookInfo(id, title, pageCount, authorId, genreId);
        } catch (DaoOperationException daoException) {
            result = daoException.getMessage();
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
    public String getCommentById(long id) {
        return Optional.ofNullable(libraryService.getCommentById(id))
                .map(Comment::toString)
                .orElse(NOT_FOUND_MESSAGE);
    }

    @ShellMethod(value = "Get all comments by book Id", key = {"get-cmts-by", "gcsb"})
    public String getAllCommentsForBook(long id) {
        String result;
        try {
            result = Optional.ofNullable(libraryService.getAllCommentsByBookId(id))
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

    @ShellMethod(value = "Add new comment", key = {"add-cmt", "ac"})
    public String addComment(String text, long bookId) {
        String result = DONE_MESSAGE;
        try {
            libraryService.saveComment(text, bookId);
        } catch (DaoOperationException daoException) {
            result = daoException.getMessage();
        } catch (Exception e) {
            result = ERROR_MESSAGE;
        }

        return result;
    }
}