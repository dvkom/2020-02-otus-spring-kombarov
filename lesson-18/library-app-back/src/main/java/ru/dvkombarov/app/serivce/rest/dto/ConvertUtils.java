package ru.dvkombarov.app.serivce.rest.dto;

import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;
import ru.dvkombarov.app.domain.Genre;

import java.util.Optional;

public class ConvertUtils {

    private ConvertUtils() {
    }

    public static BookPreviewDto toBookPreviewDto(Book book) {
        return new BookPreviewDto(
                book.getId(), book.getTitle(),
                book.getPageCount(),
                Optional.ofNullable(book.getAuthor())
                        .map(Author::getName)
                        .orElse("")
        );
    }

    public static BookFullInfoDto toBookFullInfoDto(Book book) {
        return new BookFullInfoDto(
                book.getId(),
                book.getTitle(),
                book.getPageCount(),
                Optional.ofNullable(book.getAuthor())
                        .map(Author::getName)
                        .orElse(""),
                Optional.ofNullable(book.getAuthor())
                        .map(Author::getCountry)
                        .orElse(""),
                Optional.ofNullable(book.getGenre())
                        .map(Genre::getName)
                        .orElse("")
        );
    }

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getBook().getId()
        );
    }
}
