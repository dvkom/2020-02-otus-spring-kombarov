package ru.dvkombarov.app.service.shell;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import ru.dvkombarov.app.service.business.TestingRunner;
import ru.dvkombarov.app.service.infrastructure.LocalizationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Тест команд shell ")
class ApplicationCommandsTest {

    @MockBean
    private LocalizationService localizationService;
    @MockBean
    private TestingRunner testingRunner;

    @Autowired
    private Shell shell;

    private static final String CUSTOM_LOGIN = "Вася";
    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_LOGIN_SHORT = "l";
    private static final String COMMAND_START = "s";
    private static final String COMMAND_LOGIN_PATTERN = "%s %s";
    private static final String TEST_STRING = "test";

    @DisplayName(" должен возвращать CommandNotCurrentlyAvailable для команды start если не было логина")
    @Order(1)
    @Test
    void shouldReturnCommandNotCurrentlyAvailableObjectWhenUserDoesNotLoginAfterStartCommandEvaluated() {
        doReturn(TEST_STRING).when(localizationService).getLocalValue(anyString());
        Object res = shell.evaluate(() -> COMMAND_START);
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName(" должен возвращать приветствие для всех форм команды логина")
    @Order(2)
    @Test
    void shouldReturnExpectedGreetingAfterLoginCommandEvaluated() {
        doReturn(TEST_STRING).when(localizationService).getLocalValue(anyString(), anyList());

        String res = (String) shell.evaluate(() -> COMMAND_LOGIN);
        assertThat(res).isEqualTo(TEST_STRING);

        res = (String) shell.evaluate(() -> COMMAND_LOGIN_SHORT);
        assertThat(res).isEqualTo(TEST_STRING);

        res = (String) shell.evaluate(
                () -> String.format(COMMAND_LOGIN_PATTERN, COMMAND_LOGIN_SHORT, CUSTOM_LOGIN));
        assertThat(res).isEqualTo(TEST_STRING);
    }

    @DisplayName(" должен возвращать test и вызвать метод run если start был после логина")
    @Order(3)
    @Test
    void shouldReturnExpectedMessageAndInvokeRunMethodAfterLogin() {
        doReturn(TEST_STRING).when(localizationService).getLocalValue(anyString(), anyList());
        shell.evaluate(() -> COMMAND_LOGIN);
        String res = (String) shell.evaluate(() -> COMMAND_START);
        assertThat(res).isEqualTo(TEST_STRING);
        verify(testingRunner).run();
    }
}