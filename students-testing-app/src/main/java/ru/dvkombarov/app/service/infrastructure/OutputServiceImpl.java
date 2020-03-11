package ru.dvkombarov.app.service.infrastructure;

import java.io.PrintStream;

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
