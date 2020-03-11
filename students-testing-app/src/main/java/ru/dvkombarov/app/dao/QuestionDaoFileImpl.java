package ru.dvkombarov.app.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import ru.dvkombarov.app.domain.Question;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;

public class QuestionDaoFileImpl implements QuestionDao {

    private final String pathToFile;

    public QuestionDaoFileImpl(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    @Override
    public Collection<Question> readAllQuestions() {
        try (InputStream inputStream = getClass().getResourceAsStream(pathToFile)) {
            CsvToBean<Question> csvToBean =
                    new CsvToBeanBuilder<Question>(new InputStreamReader(inputStream))
                            .withType(Question.class)
                            .build();

            return csvToBean.parse();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
