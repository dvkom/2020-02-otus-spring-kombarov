package ru.dvkombarov.app.service.business;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.dvkombarov.app.dao.QuestionDao;
import ru.dvkombarov.app.domain.Question;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.mockito.Mockito.doReturn;

@DisplayName("Класс QuestionServiceImpl")
@TestInstance(Lifecycle.PER_CLASS)
class QuestionServiceImplTest {

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Mock
    private QuestionDao questionDao;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("должен возвращать коллекцию Question")
    @Test
    void shouldReturnQuestions() {
        Question question = new Question();
        doReturn(Collections.singletonList(question)).when(questionDao).readAllQuestions();
        Collection<Question> questions = questionService.getAllQuestions();

        assertThat(questions).containsOnly(question);
    }
}