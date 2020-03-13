package ru.dvkombarov.app.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.dvkombarov.app.domain.Question;
import ru.dvkombarov.app.service.infrastructure.LocalizationService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;

@Service
public class QuestionDaoFileImpl implements QuestionDao {

    private final LocalizationService localizationService;
    private final String defaultPathToFile;

    public QuestionDaoFileImpl(LocalizationService localizationService,
                               @Value("${dao.pathToFile}") String defaultPathToFile) {
        this.localizationService = localizationService;
        this.defaultPathToFile = defaultPathToFile;
    }

    @Override
    public Collection<Question> readAllQuestions() {
        try (InputStream inputStream = getClass().getResourceAsStream(getPathToFile())) {
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

    private String getPathToFile() {
        String pathToFile = localizationService.getLocalValue("dao.pathToFile");

        return pathToFile == null ? defaultPathToFile : pathToFile;
    }
}
