package ru.dvkombarov.app.rest.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.dvkombarov.app.configuration.AuthProperties;
import ru.dvkombarov.app.domain.User;
import ru.dvkombarov.app.exceptions.BadRequestException;
import ru.dvkombarov.app.repository.UserRepository;
import ru.dvkombarov.app.rest.dto.AuthResponse;
import ru.dvkombarov.app.rest.dto.LoginRequest;
import ru.dvkombarov.app.rest.dto.SignUpRequest;
import ru.dvkombarov.app.rest.dto.SingUpResponse;
import ru.dvkombarov.app.security.TokenProvider;

import javax.validation.Valid;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final Logger LOG = LoggerFactory.getLogger(AuthController.class);
  private static final String INTERNAL_ERROR_MESSAGE = "An error occurred while processing " +
      "the request on the server";

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;
  private final AuthProperties authProperties;

  public AuthController(AuthenticationManager authenticationManager,
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        TokenProvider tokenProvider,
                        AuthProperties authProperties) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenProvider = tokenProvider;
    this.authProperties = authProperties;
  }

  @PostMapping("/login")
  public AuthResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    LOG.debug("Authentication requested, email={}", loginRequest.getEmail());

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getEmail(),
            loginRequest.getPassword()
        )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = tokenProvider.createToken(authentication);

    LOG.debug("Authentication successful, email={}", loginRequest.getEmail());

    return new AuthResponse(token, authProperties.getTokenType());
  }

  @PostMapping("/signup")
  public SingUpResponse registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    LOG.debug("Registration requested, email={}", signUpRequest.getEmail());

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      throw new BadRequestException(
          format("Email address %s already exist.", signUpRequest.getEmail())
      );
    }

    User user = new User();
    user.setName(signUpRequest.getName());
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(signUpRequest.getPassword());
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    userRepository.save(user);

    LOG.debug("Registration successful, email={}", signUpRequest.getEmail());

    return new SingUpResponse(true, "User registered successfully");
  }

  @ExceptionHandler({AuthenticationException.class, MethodArgumentNotValidException.class})
  public ResponseEntity<String> handleBadRequestException(Exception e) {
    LOG.error(e.getMessage(), e);

    return ResponseEntity.badRequest()
        .contentType(MediaType.APPLICATION_JSON)
        .body(e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleAnotherException(Exception e) {
    LOG.error(INTERNAL_ERROR_MESSAGE, e);

    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_JSON)
        .body(INTERNAL_ERROR_MESSAGE);
  }
}
