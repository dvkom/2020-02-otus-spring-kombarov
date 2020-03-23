package ru.dvkombarov.app.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.dvkombarov.app.configs.YamlProps;
import ru.dvkombarov.app.domain.Question;
import ru.dvkombarov.app.service.infrastructure.LocalizationService;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@DisplayName("Класс QuestionDaoFileImpl")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuestionDaoFileImplTest {

    private static final String PATH_TO_FILE = "/questions_test.csv";

    @InjectMocks
    private QuestionDaoFileImpl questionDao;

    @Mock
    private LocalizationService localizationService;
    @Mock
    private YamlProps yamlProps;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("должен вернуть пустую коллекцию")
    @Test
    void shouldReturnEmptyCollection() {
        doReturn("").when(yamlProps).getPathToFile();
        assertThat(questionDao.readAllQuestions()).isEmpty();
    }

    @DisplayName("должен вернуть коллекцию c 2 вопросами")
    @Test
    void shouldReturnCollectionWithTwoQuestions() {
        doReturn(PATH_TO_FILE).when(yamlProps).getPathToFile();
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