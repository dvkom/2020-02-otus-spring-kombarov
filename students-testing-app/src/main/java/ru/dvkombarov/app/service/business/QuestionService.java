package ru.dvkombarov.app.service.business;

import ru.dvkombarov.app.domain.Question;

import java.util.Collection;

public interface QuestionService {
    Collection<Question> getAllQuestions();
}
