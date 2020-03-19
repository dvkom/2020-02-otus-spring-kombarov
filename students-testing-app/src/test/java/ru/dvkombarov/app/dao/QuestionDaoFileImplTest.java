package ru.dvkombarov.app.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import ru.dvkombarov.app.domain.Question;
import ru.dvkombarov.app.service.infrastructure.LocalizationService;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс QuestionDaoFileImpl")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuestionDaoFileImplTest {

    private QuestionDaoFileImpl questionDao;
    private LocalizationService localizationService;

    @BeforeAll
    void init() {
        localizationService = Mockito.mock(LocalizationService.class);
    }

    @DisplayName("должен вернуть пустую коллекцию")
    @Test
    void shouldReturnEmptyCollection() {
        questionDao = new QuestionDaoFileImpl(localizationService, "");
        assertThat(questionDao.readAllQuestions()).isEmpty();
    }

    @DisplayName("должен вернуть коллекцию c 2 вопросами")
    @Test
    void shouldReturnCollectionWithTwoQuestions() {
        questionDao = new QuestionDaoFileImpl(localizationService, "/questions_test.csv");
        Collection<Question> questions = questionDao.readAllQuestions();
        assertThat(questions).isNotEmpty();
        assertThat(questions).containsOnly(prepareQuestions());
    }

    private Question[] prepareQuestions() {
        Question question1 = new Question();
        question1.setNumber("1");
        question1.setText("Test 1");
        question1.setAnswer("");
        Question question2 = new Question();
        question2.setNumber("2");
        question2.setText("Test 2");
        question2.setAnswer("2");

        return new Question[] {question1, question2};
    }
}