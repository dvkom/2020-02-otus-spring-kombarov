package ru.dvkombarov.app.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс QuestionDaoFileImpl")
class QuestionDaoFileImplTest {

    @DisplayName("должен вернуть пустую коллекцию")
    @Test
    void shouldReturnEmptyCollection() {
        QuestionDaoFileImpl questionDao = new QuestionDaoFileImpl("");
        assertThat(questionDao.readAllQuestions()).isEmpty();
    }
}