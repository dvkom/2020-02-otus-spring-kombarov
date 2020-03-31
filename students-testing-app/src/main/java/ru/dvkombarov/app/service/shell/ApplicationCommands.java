package ru.dvkombarov.app.service.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.dvkombarov.app.service.business.TestingRunner;
import ru.dvkombarov.app.service.infrastructure.LocalizationService;

import java.util.List;

@ShellComponent
public class ApplicationCommands {

    private final LocalizationService localizationService;
    private final TestingRunner testingRunner;
    private String userName;

    public ApplicationCommands(LocalizationService localizationService,
                               TestingRunner testingRunner) {
        this.localizationService = localizationService;
        this.testingRunner = testingRunner;
    }

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "anonymous") String userName) {
        this.userName = userName;
        return localizationService.getLocalValue("shell.greeting", List.of(userName));
    }

    @ShellMethod(value = "Start testing", key = {"s", "start"})
    @ShellMethodAvailability(value = "isUserLoggedIn")
    public String startTesting() {
        testingRunner.run();
        return localizationService.getLocalValue("shell.endTesting", List.of(userName));
    }

    private Availability isUserLoggedIn() {
        String message = localizationService.getLocalValue("shell.unavailable");
        return userName == null ? Availability.unavailable(message) : Availability.available();
    }
}
