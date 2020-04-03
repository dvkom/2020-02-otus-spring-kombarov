package ru.dvkombarov.app.serivce.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Genre;
import ru.dvkombarov.app.exceptions.DaoOperationException;
import ru.dvkombarov.app.serivce.businnes.LibraryService;
import ru.dvkombarov.app.utils.DateUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
public class ApplicationCommands {

    private static final String DONE_MESSAGE = "Сделано!";
    private static final String ERROR_MESSAGE = "Произошла ошибка...";
    private static final String NOT_FOUND_MESSAGE = "Книга не найдена";
    private final LibraryService libraryService;

    public ApplicationCommands(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @ShellMethod(value = "Get all books", key = {"get-all"})
    public String getAllBooks() {
        String result;
        try {
            result = Optional.ofNullable(libraryService.getAllBooks())
                    .stream()
                    .flatMap(Collection::stream)
                    .map(Book::toString)
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            result = ERROR_MESSAGE;
        }

        return result;
    }

    @ShellMethod(value = "Get a book by Id", key = {"get"})
    public String getBookById(long id) {
        return Optional.ofNullable(libraryService.getBookById(id))
                .map(Book::toString)
                .orElse(NOT_FOUND_MESSAGE);
    }

    @ShellMethod(value = "Delete a book by Id", key = {"delete"})
    public String deleteBookById(long id) {
        String result = DONE_MESSAGE;
        try {
            libraryService.deleteBookById(id);
        } catch (Exception e) {
            result = ERROR_MESSAGE;
        }

        return result;
    }

    @ShellMethod(value = "Add new book", key = {"add"})
    public String addBook(long id, String title, int pageCount, long authorId, long genreId,
                          @ShellOption(defaultValue = "") String authorName,
                          @ShellOption(defaultValue = "") String authorCountry,
                          @ShellOption(defaultValue = "") String authorBirthDate,
                          @ShellOption(defaultValue = "") String genreName,
                          @ShellOption(defaultValue = "") String genreDescription) {
        String result = DONE_MESSAGE;
        try {
            libraryService.saveBook(
                    new Book(id, title, pageCount, new Author(authorId, authorName, authorCountry,
                            DateUtils.getDateFromString(authorBirthDate)),
                            new Genre(genreId, genreName, genreDescription)
                    )
            );
        } catch (DaoOperationException daoException) {
            result = daoException.getMessage();
        } catch (Exception e) {
            result = ERROR_MESSAGE;
        }

        return result;
    }

    @ShellMethod(value = "Update a book info", key = {"update"})
    public String updateBookInfo(long id, String title, int pageCount, long authorId, long genreId,
                                 @ShellOption(defaultValue = "") String authorName,
                                 @ShellOption(defaultValue = "") String authorCountry,
                                 @ShellOption(defaultValue = "") String authorBirthDate,
                                 @ShellOption(defaultValue = "") String genreName,
                                 @ShellOption(defaultValue = "") String genreDescription) {
        String result = DONE_MESSAGE;
        try {
            libraryService.updateBookInfo(
                    new Book(id, title, pageCount, new Author(authorId, authorName, authorCountry,
                            DateUtils.getDateFromString(authorBirthDate)),
                            new Genre(genreId, genreName, genreDescription)
                    )
            );
        } catch (DaoOperationException daoException) {
            result = daoException.getMessage();
        } catch (Exception e) {
            result = ERROR_MESSAGE;
        }

        return result;
    }
}