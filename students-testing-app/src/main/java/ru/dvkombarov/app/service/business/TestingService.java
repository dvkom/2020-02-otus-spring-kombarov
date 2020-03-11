package ru.dvkombarov.app.service.business;

import ru.dvkombarov.app.domain.Question;

import java.util.Collection;

public interface TestingService {
    void test(Collection<Question> questions);
}
