package ru.dvkombarov.app.service.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс InputServiceImpl")
class InputServiceImplTest {

    @DisplayName("должен корректно возвращать строку ввода")
    @Test
    void shouldCorrectReturnInputString() {
        String input = "input";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        InputServiceImpl inputService = new InputServiceImpl(inputStream);
        String line = inputService.readLine();
        assertThat(line).isEqualTo(input);
    }
}