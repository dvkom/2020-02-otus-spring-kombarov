package ru.dvkombarov.app.rest.dto;

import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;
import ru.dvkombarov.app.domain.Genre;

import java.util.Optional;

public class ConvertUtils {

    private ConvertUtils() {
    }

    public static BookDto toBookDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                Optional.ofNullable(book.getAuthor())
                        .map(Author::getName)
                        .orElse(""),
                Optional.ofNullable(book.getGenre())
                        .map(Genre::getName)
                        .orElse("")
        );
    }

    public static CommentDto toCommentDto(Book book, Comment comment) {
        return toCommentDto(book.getId(), comment);
    }

    public static CommentDto toCommentDto(String bookId, Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                bookId
        );
    }

    public static Book toBook(BookDto bookDto) {
        return new Book(
                bookDto.getTitle(),
                new Author(bookDto.getAuthorName()),
                new Genre(bookDto.getGenreName())
        );
    }

    public static Comment toComment(CommentDto commentDto) {
        return new Comment(commentDto.getText());
    }
}
