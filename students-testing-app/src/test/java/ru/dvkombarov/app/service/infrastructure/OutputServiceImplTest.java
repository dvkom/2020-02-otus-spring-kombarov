package ru.dvkombarov.app.service.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс OutputServiceImpl")
class OutputServiceImplTest {

    @DisplayName("должен записывать вывод в PrintStream")
    @Test
    void shouldWriteOutputIntoPrintStream() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputServiceImpl outputService = new OutputServiceImpl(new PrintStream(outputStream));
        String output = "output";
        outputService.writeLine(output);
        assertThat(outputStream.toString()).isEqualToIgnoringNewLines(output);
    }

}