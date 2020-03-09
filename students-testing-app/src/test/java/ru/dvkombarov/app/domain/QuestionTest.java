package ru.dvkombarov.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Question")
class QuestionTest {

    @DisplayName("корректно работают сеттеры")
    @Test
    void shouldHaveCorrectSetters() {
        String number = "1";
        String text = "text";
        String answer = "answer";
        Question question = new Question();
        question.setNumber(number);
        question.setText(text);
        question.setAnswer(answer);

        assertThat(question.getNumber()).isEqualTo(number);
        assertThat(question.getText()).isEqualTo(text);
        assertThat(question.getAnswer()).isEqualTo(answer);
    }
}