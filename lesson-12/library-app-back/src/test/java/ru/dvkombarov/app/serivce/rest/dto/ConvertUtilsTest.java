package ru.dvkombarov.app.serivce.rest.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;
import ru.dvkombarov.app.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Методы класса ConvertUtils должны ")
class ConvertUtilsTest {

    @Test
    @DisplayName(" конвертировать книгу в ДТО")
    void shouldConvertToBookPreviewDto() {
        long id = 1;
        String title = "title";
        int pageCount = 123;
        String authorName = "name";
        BookPreviewDto expected = new BookPreviewDto(id, title, pageCount, authorName);
        BookPreviewDto actual = ConvertUtils.toBookPreviewDto(new Book(
                id, title, pageCount,
                new Author(0, authorName, ""),
                new Genre(0, "")
        ));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName(" конвертировать книгу в ДТО с полной информацией")
    void shouldConvertToBookFullInfoDto() {
        long id = 1;
        String title = "title";
        int pageCount = 123;
        String authorName = "name";
        String authorCountry = "country";
        String genreName = "genre";
        BookFullInfoDto expected = new BookFullInfoDto(id, title, pageCount, authorName,
                authorCountry, genreName);
        BookFullInfoDto actual = ConvertUtils.toBookFullInfoDto(new Book(
                id, title, pageCount,
                new Author(0, authorName, authorCountry),
                new Genre(0, genreName)
        ));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName(" конвертировать комментарий в ДТО")
    void shouldConvertToCommentDto() {
        long id = 1;
        String text = "text";
        long bookId = 2;
        CommentDto expected = new CommentDto(id, text, bookId);
        CommentDto actual = ConvertUtils.toCommentDto(new Comment(
                id, text, new Book(bookId, "", 0, null, null)
        ));

        assertThat(actual).isEqualTo(expected);
    }
}