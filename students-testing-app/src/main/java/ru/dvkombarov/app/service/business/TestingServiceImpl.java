package ru.dvkombarov.app.service.business;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.dvkombarov.app.domain.Question;
import ru.dvkombarov.app.service.infrastructure.InputService;
import ru.dvkombarov.app.service.infrastructure.LocalizationService;
import ru.dvkombarov.app.service.infrastructure.OutputService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestingServiceImpl implements TestingService {

    private final InputService inputService;
    private final OutputService outputService;
    private final LocalizationService localizationService;

    public TestingServiceImpl(InputService inputService,
                              OutputService outputService,
                              LocalizationService localizationService) {
        this.inputService = inputService;
        this.outputService = outputService;
        this.localizationService = localizationService;
    }

    @Override
    public void test(Collection<Question> questions) {
        if (CollectionUtils.isEmpty(questions)) {
            outputService.writeLine(localizationService.getLocalValue(
                    "testing.emptyQuestions"));

            return;
        }

        outputService.writeLine(localizationService.getLocalValue(
                "testing.greeting"));
        List<Boolean> testingResult = questions.stream()
                .map(this::askQuestion)
                .collect(Collectors.toList());

        printResult(testingResult);
    }

    private Boolean askQuestion(@NonNull Question question) {
        outputService.writeLine(localizationService.getLocalValue(
                "testing.questionTemplate",
                List.of(question.getNumber(), question.getText())
        ));
        String answer = inputService.readLine();

        // Если в questions отсутствует правильный ответ, значит любой ответ правильный
        return Optional.ofNullable(question.getAnswer())
                .map(correctAnswer ->
                        correctAnswer.isEmpty() || correctAnswer.equalsIgnoreCase(answer))
                .orElse(Boolean.TRUE);
    }

    private void printResult(@NonNull List<Boolean> results) {
        long correctAnswersNumber = results.stream()
                .filter(Boolean.TRUE::equals)
                .count();

        outputService.writeLine(localizationService.getLocalValue(
                "testing.resultTemplate",
                List.of(String.valueOf(correctAnswersNumber), String.valueOf(results.size()))
        ));
    }
}
