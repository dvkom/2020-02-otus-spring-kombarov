package ru.dvkombarov.app.service.infrastructure;

import java.util.List;

public interface LocalizationService {
    String getLocalValue(String messageName);

    String getLocalValue(String messageName, List<String> parameters);
}
