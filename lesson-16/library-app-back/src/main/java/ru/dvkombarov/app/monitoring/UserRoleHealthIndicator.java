package ru.dvkombarov.app.monitoring;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.dvkombarov.app.domain.User;
import ru.dvkombarov.app.repository.UserRepository;

import java.util.List;
import java.util.Map;

import static ru.dvkombarov.app.security.Roles.EDITOR;
import static ru.dvkombarov.app.security.Roles.USER;

@Component
public class UserRoleHealthIndicator implements HealthIndicator {

    static final String EDITORS = "Editors";
    static final String USERS = "Users";

    private UserRepository userRepository;

    public UserRoleHealthIndicator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Health health() {
        try {
            List<User> users = userRepository.findAll();
            long editorsCount = users
                    .stream()
                    .filter(user -> user.getRole().equals(EDITOR))
                    .count();
            long usersCount = users
                    .stream()
                    .filter(user -> user.getRole().equals(USER))
                    .count();

            Map<String, Long> details = Map.of(EDITORS, editorsCount, USERS, usersCount);
            if (editorsCount > 0 && usersCount > 0) {
                return Health.up().withDetails(details).build();
            }

            return Health.down().withDetails(details).build();
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }
}
