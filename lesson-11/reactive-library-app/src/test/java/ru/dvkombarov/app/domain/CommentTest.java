package ru.dvkombarov.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("В классе Comment должен ")
class CommentTest {


    @DisplayName("корректно работать конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        String text = "name";
        Comment comment = new Comment(text);

        assertThat(comment.getText()).isEqualTo(text);
    }
}
