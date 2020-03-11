package ru.dvkombarov.app.dao;

import ru.dvkombarov.app.domain.Question;

import java.util.Collection;

public interface QuestionDao {
    Collection<Question> readAllQuestions();
}
