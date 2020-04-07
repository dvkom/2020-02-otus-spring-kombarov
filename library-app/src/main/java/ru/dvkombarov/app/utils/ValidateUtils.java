package ru.dvkombarov.app.utils;

import org.springframework.util.StringUtils;
import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Genre;

public final class ValidateUtils {
    private ValidateUtils() {
    }

    public static boolean isValid(Book book) {
        return book == null || book.getAuthor() == null || book.getGenre() == null;
    }

    public static boolean isValid(Author author) {
        return !StringUtils.isEmpty(author.getName()) &&
                !StringUtils.isEmpty(author.getCountry()) &&
                author.getBirthDate() != null;
    }

    public static boolean isValid(Genre genre) {
        return !StringUtils.isEmpty(genre.getName());
    }
}
