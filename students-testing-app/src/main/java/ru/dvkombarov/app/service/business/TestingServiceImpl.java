package ru.dvkombarov.app.service.business;

import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import ru.dvkombarov.app.domain.Question;
import ru.dvkombarov.app.service.infrastructure.InputService;
import ru.dvkombarov.app.service.infrastructure.OutputService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestingServiceImpl implements TestingService {

    private final InputService inputService;
    private final OutputService outputService;

    public TestingServiceImpl(InputService inputService, OutputService outputService) {
        this.inputService = inputService;
        this.outputService = outputService;
    }

    @Override
    public void test(Collection<Question> questions) {
        if (CollectionUtils.isEmpty(questions)) {
            outputService.writeLine("Отсутствуют вопросы для тестирования");

            return;
        }

        outputService.writeLine("Пожалуйста, ответьте на следующие вопросы:");
        List<Boolean> testingResult = questions.stream()
                .map(this::askQuestion)
                .collect(Collectors.toList());

        printResult(testingResult);
    }

    private Boolean askQuestion(@NonNull Question question) {
        outputService.writeLine(String.format("Вопрос №%s. %s",
                question.getNumber(), question.getText()));
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

        outputService.writeLine(String.format("Результат: %s правильных ответов из %s",
                correctAnswersNumber, results.size()));
    }
}
