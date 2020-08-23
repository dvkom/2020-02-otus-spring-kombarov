package ru.dvkombarov.app.rest.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import ru.dvkombarov.app.configuration.AuthProperties;
import ru.dvkombarov.app.repository.UserRepository;
import ru.dvkombarov.app.rest.dto.LoginRequest;
import ru.dvkombarov.app.rest.dto.SignUpRequest;
import ru.dvkombarov.app.security.CustomUserDetailsService;
import ru.dvkombarov.app.security.RestAuthenticationEntryPoint;
import ru.dvkombarov.app.security.TokenProvider;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureWebClient
@Import({AuthProperties.class, CustomUserDetailsService.class, RestAuthenticationEntryPoint.class})

@DisplayName("Methods of the auth controller should ")
class AuthControllerTest {

  @MockBean
  private TokenProvider tokenProvider;
  @MockBean
  private AuthenticationManager authenticationManager;
  @MockBean
  private UserRepository userRepository;
  @MockBean
  private PasswordEncoder passwordEncoder;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName(" return successful status auth response")
  void shouldReturnSuccessAnswerWhenLogin() throws Exception {
    doReturn("A am token").when(tokenProvider).createToken(any());
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setPassword("pass");
    loginRequest.setEmail("email@test.ru");
    mockMvc.perform(post("/auth/login")
        .content(asJsonString(loginRequest))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName(" return bad request status response when login failed")
  void shouldReturnBadRequestWhenLoginFailed() throws Exception {
    doThrow(BadCredentialsException.class).when(authenticationManager).authenticate(any());
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setPassword("pass");
    loginRequest.setEmail("email@test.ru");
    mockMvc.perform(post("/auth/login")
        .content(asJsonString(loginRequest))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName(" return bad request status response when login invalid")
  void shouldReturnBadRequestWhenLoginNotValid() throws Exception {
    mockMvc.perform(post("/auth/login")
        .content(asJsonString(new LoginRequest()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName(" return internal server error status response when something went wrong")
  void shouldInternalServerErrorRequestWhenSomethingWentWrong() throws Exception {
    doThrow(RuntimeException.class).when(authenticationManager).authenticate(any());
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setPassword("pass");
    loginRequest.setEmail("email@test.ru");
    mockMvc.perform(post("/auth/login")
        .content(asJsonString(loginRequest))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName(" return successful status sing up response")
  void shouldReturnSuccessAnswerWhenSingUp() throws Exception {
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setPassword("pass");
    signUpRequest.setEmail("email@test.ru");
    signUpRequest.setName("name");
    mockMvc.perform(post("/auth/signup")
        .content(asJsonString(signUpRequest))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}