package ru.dvkombarov.app.service.business;

import ru.dvkombarov.app.dao.QuestionDao;
import ru.dvkombarov.app.domain.Question;

import java.util.Collection;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public Collection<Question> getAllQuestions() {
        return questionDao.readAllQuestions();
    }
}
