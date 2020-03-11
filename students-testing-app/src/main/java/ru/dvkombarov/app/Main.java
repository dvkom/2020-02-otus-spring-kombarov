package ru.dvkombarov.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.dvkombarov.app.domain.Question;
import ru.dvkombarov.app.service.business.QuestionService;
import ru.dvkombarov.app.service.business.TestingService;

import java.util.Collection;

public class Main {

    private static final String CONFIG_LOCATION = "/spring-context.xml";

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext(CONFIG_LOCATION);

        QuestionService questionService = context.getBean(QuestionService.class);
        TestingService testingService = context.getBean(TestingService.class);

        Collection<Question> questions = questionService.getAllQuestions();
        testingService.test(questions);
    }
}
