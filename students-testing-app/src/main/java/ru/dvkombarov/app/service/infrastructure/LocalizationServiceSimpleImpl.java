package ru.dvkombarov.app.service.infrastructure;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class LocalizationServiceSimpleImpl implements LocalizationService {

    private final MessageSource messageSource;

    public LocalizationServiceSimpleImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getLocalValue(String valueName) {
        return getValue(valueName, null);
    }

    @Override
    public String getLocalValue(String valueName, List<String> parameters) {
        return getValue(valueName, parameters);
    }

    private String getValue(String valueName, List<String> parameters) {
        String value = null;
        try {
            value = messageSource.getMessage(
                    valueName,
                    parameters == null ? null : parameters.toArray(),
                    Locale.getDefault());
        } catch (NoSuchMessageException ignored) {}

        return value;
    }
}
