package ru.dvkombarov.app.service.business;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.dvkombarov.app.domain.Question;

import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.*;

@DisplayName("Класс TestingRunnerImpl")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestingRunnerImplTest {

    @InjectMocks
    private TestingRunnerImpl testingRunnerImpl;

    @Mock
    private QuestionService questionService;

    @Mock
    private TestingService testingService;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("должен запускать тестирование")
    @Test
    void shouldRunTesting() {
        Collection<Question> questions = List.of(new Question());
        doReturn(questions).when(questionService).getAllQuestions();
        doNothing().when(testingService).test(anyCollection());
        testingRunnerImpl.run();
        verify(questionService).getAllQuestions();
        verify(testingService).test(eq(questions));
    }
}