package ru.dvkombarov.app.service.business;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.dvkombarov.app.domain.Question;
import ru.dvkombarov.app.service.infrastructure.InputService;
import ru.dvkombarov.app.service.infrastructure.LocalizationService;
import ru.dvkombarov.app.service.infrastructure.OutputService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.TestInstance.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Класс TestingServiceImpl")
@TestInstance(Lifecycle.PER_CLASS)
class TestingServiceImplTest {

    @InjectMocks
    private TestingServiceImpl testingService;

    @Mock
    private InputService inputService;

    @Mock
    private OutputService outputService;

    @Mock
    private LocalizationService localizationService;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        doReturn("").when(localizationService).getLocalValue(anyString());
    }

    @DisplayName("если нет Question, вызвать только один раз OutputService")
    @Test
    void whenQuestionsIsEmptyShouldOnlyOnceCallOutputService() {
        resetInteractions();
        testingService.test(Collections.emptyList());
        verify(inputService, never()).readLine();
        verify(outputService).writeLine(anyString());
    }

    @DisplayName("количество вызовов InputService соответствует количеству Question")
    @Test
    void inputServiceCallCountShouldMatchWithQuestionsCount() {
        resetInteractions();
        Question question = new Question();
        question.setNumber("1");
        question.setText("");
        question.setAnswer("");
        doReturn("").when(localizationService).getLocalValue(anyString(), anyList());
        testingService.test(List.of(question, question, question));
        verify(inputService, times(3)).readLine();
    }

    private void resetInteractions() {
        Mockito.reset(inputService);
        Mockito.reset(outputService);
    }
}