package ru.dvkombarov.app.monitoring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.dvkombarov.app.domain.User;
import ru.dvkombarov.app.repository.UserRepository;
import ru.dvkombarov.app.security.Roles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static ru.dvkombarov.app.monitoring.UserRoleHealthIndicator.EDITORS;
import static ru.dvkombarov.app.monitoring.UserRoleHealthIndicator.USERS;

@SpringBootTest
@DisplayName("Методы health-indicator должны ")
class UserRoleHealthIndicatorTest {

    @Autowired
    private UserRoleHealthIndicator userRoleHealthIndicator;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("возвращять статус UP когда есть пользователи с ролями user и editor")
    void shouldReturnUp_whenEditorsAndUsersExist() {
        User editor = new User();
        editor.setRole(Roles.EDITOR);
        User user = new User();
        user.setRole(Roles.USER);
        doReturn(List.of(editor, user)).when(userRepository).findAll();

        Health result = userRoleHealthIndicator.health();
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(Status.UP);
        assertThat(result.getDetails().get(EDITORS)).isEqualTo(1L);
        assertThat(result.getDetails().get(USERS)).isEqualTo(1L);
    }

    @Test
    @DisplayName("возвращять статус DOWN когда нет пользователей с ролью user или editor")
    void shouldReturnDown_whenEditorsAndUsersNotExist() {
        User tech = new User();
        tech.setRole(Roles.TECH_SUPPORT);
        doReturn(List.of(tech)).when(userRepository).findAll();

        Health result = userRoleHealthIndicator.health();
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(Status.DOWN);
        assertThat(result.getDetails().get(EDITORS)).isEqualTo(0L);
        assertThat(result.getDetails().get(USERS)).isEqualTo(0L);
    }

    @Test
    @DisplayName("возвращять статус DOWN при ошибке")
    void shouldReturnDown_whenExceptionHappened() {
        doThrow(new RuntimeException()).when(userRepository).findAll();

        Health result = userRoleHealthIndicator.health();
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(Status.DOWN);
        assertThat(result.getDetails().get("error")).isNotNull();
    }
}