package ru.dvkombarov.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("В классе Comment должен ")
public class CommentTest {


    @DisplayName("корректно работать конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        long id = 1;
        String text = "name";
        Book book = new Book();
        Comment comment = new Comment(id, text, book);

        assertThat(comment.getId()).isEqualTo(id);
        assertThat(comment.getText()).isEqualTo(text);
        assertThat(comment.getBook()).isEqualTo(book);
    }
}
