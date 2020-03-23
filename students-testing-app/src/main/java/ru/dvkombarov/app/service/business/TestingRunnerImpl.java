package ru.dvkombarov.app.service.business;

import org.springframework.stereotype.Service;
import ru.dvkombarov.app.domain.Question;

import java.util.Collection;

@Service
public class TestingRunnerImpl implements TestingRunner {

    private final QuestionService questionService;
    private final TestingService testingService;

    public TestingRunnerImpl(QuestionService questionService,
                             TestingService testingService) {
        this.questionService = questionService;
        this.testingService = testingService;
    }

//    @Override
//    public void run(String... args) {
//        Collection<Question> questions = questionService.getAllQuestions();
//        testingService.test(questions);
//    }

    @Override
    public void run() {
        Collection<Question> questions = questionService.getAllQuestions();
        testingService.test(questions);
    }
}
