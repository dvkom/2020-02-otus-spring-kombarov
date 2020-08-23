package ru.dvkombarov.app.security;

import org.springframework.security.core.Authentication;

public interface TokenProvider {
  String createToken(Authentication authentication);

  Long getUserIdFromToken(String token);

  boolean validateToken(String authToken);
}
