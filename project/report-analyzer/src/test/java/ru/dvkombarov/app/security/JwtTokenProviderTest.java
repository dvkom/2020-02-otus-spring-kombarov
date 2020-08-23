package ru.dvkombarov.app.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import ru.dvkombarov.app.configuration.AuthProperties;
import ru.dvkombarov.app.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@SpringBootConfiguration
@EnableConfigurationProperties
@Import({AuthProperties.class, JwtTokenProvider.class})
@DisplayName("Methods of the token provider should ")
class JwtTokenProviderTest {

  private static final String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTk4MTgxMDM1LC" +
      "JleHAiOjE1OTgyNjc0MzV9.idM86k4KT4wqP4Z0y0TMZ6KYuvqBx0Hf67bmDpHdPti0FFOtzMIJkCaic8KpMwyuAfg-" +
      "yx6TF6E9akdn4mvLAg";

  @Autowired
  private JwtTokenProvider tokenProvider;

  @Test
  @DisplayName("return a token ")
  void shouldReturnVulnersByStream() {
    User user = new User("asd", "asd@asd.ru", "asd");
    user.setId(1L);
    Authentication authentication = new UsernamePasswordAuthenticationToken(
        UserPrincipal.create(user), null
    );

    String actualToken = tokenProvider.createToken(authentication);

    assertThat(actualToken).isNotBlank();
  }

  @Test
  @DisplayName("throw an exception ")
  void shouldThrowException() {
    assertThrows(NullPointerException.class, () -> tokenProvider.createToken(null));
  }

  @Test
  @DisplayName("return user id from token ")
  void shouldReturnUserIdFromToken() {
    Long id = tokenProvider.getUserIdFromToken(token);

    assertThat(id).isEqualTo(1L);
  }

  @Test
  @DisplayName("return true when token is valid ")
  void shouldReturnTrueWhenTokenIsValid() {
    boolean isValid = tokenProvider.validateToken(token);

    assertThat(isValid).isTrue();
  }
}