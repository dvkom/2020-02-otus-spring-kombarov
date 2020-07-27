package ru.dvkombarov.app.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.dvkombarov.app.domain.User;
import ru.dvkombarov.app.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@DisplayName("Методы сервиса пользователей должны ")
class UserDetailsServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Test
    @DisplayName("возвращять пользователя по userName")
    void shouldReturnUserByName() {
        String userName = "Bob";
        User user = new User();
        user.setName(userName);
        doReturn(Optional.of(user)).when(userRepository).getByName(anyString());

        UserDetails userDetails = userDetailsService.loadUserByUsername("Bob");
        assertThat(userDetails).isNotNull();
        assertThat(userDetails instanceof UserPrincipal).isTrue();
        assertThat((UserPrincipal) userDetails)
                .extracting(UserPrincipal::getUsername)
                .isEqualTo(userName);

    }

    @Test
    @DisplayName("кидать exception если имя пользователя не найдено")
    void shouldThrowExceptionWhenUserNotFound() {
        String userName = "Bob";
        doReturn(Optional.empty()).when(userRepository).getByName(anyString());

        Throwable thrown = catchThrowable(() -> userDetailsService.loadUserByUsername(userName));
        assertThat(thrown).isInstanceOf(UsernameNotFoundException.class)
                .hasMessage(userName);
    }
}