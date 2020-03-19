package ru.dvkombarov.app.service.infrastructure;

import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class OutputServiceImpl implements OutputService {

    private final PrintStream printStream;

    public OutputServiceImpl(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void writeLine(String line) {
        printStream.println(line);
    }
}
