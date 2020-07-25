package ru.dvkombarov.app.service;

import org.springframework.integration.core.GenericSelector;
import org.springframework.stereotype.Service;
import ru.dvkombarov.app.domain.Book;

@Service
public class NoMoreDetectivesFilter implements GenericSelector<Book> {

    private static final String FILTERED_GENRE = "Detective fiction";

    @Override
    public boolean accept(Book source) {
        return !source.getGenre().equals(FILTERED_GENRE);
    }
}
