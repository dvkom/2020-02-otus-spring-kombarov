package ru.dvkombarov.app.service.infrastructure;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class InputServiceImpl implements InputService {

    private final Scanner scanner;

    public InputServiceImpl(InputStream inputStream) {
        scanner = new Scanner(new InputStreamReader(inputStream));
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
