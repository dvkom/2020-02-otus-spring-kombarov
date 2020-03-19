package ru.dvkombarov.app.service.business;

import org.springframework.stereotype.Service;
import ru.dvkombarov.app.dao.QuestionDao;
import ru.dvkombarov.app.domain.Question;

import java.util.Collection;

@Service
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
