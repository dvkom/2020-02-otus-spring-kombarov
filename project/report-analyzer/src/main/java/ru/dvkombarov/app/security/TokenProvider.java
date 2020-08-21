package ru.dvkombarov.app.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dvkombarov.app.configuration.AuthProperties;

import java.util.Date;

@Service
public class TokenProvider {

  private static final Logger LOG = LoggerFactory.getLogger(TokenProvider.class);

  private AuthProperties authProperties;

  public TokenProvider(AuthProperties authProperties) {
    this.authProperties = authProperties;
  }

  public String createToken(Authentication authentication) {
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + authProperties.getTokenExpirationMsec());

    return Jwts.builder()
        .setSubject(Long.toString(userPrincipal.getId()))
        .setIssuedAt(new Date())
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, authProperties.getTokenSecret())
        .compact();
  }

  Long getUserIdFromToken(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(authProperties.getTokenSecret())
        .parseClaimsJws(token)
        .getBody();

    return Long.parseLong(claims.getSubject());
  }

  boolean validateToken(String authToken) {
    try {
      Jwts.parser()
          .setSigningKey(authProperties.getTokenSecret())
          .parseClaimsJws(authToken);

      return true;
    } catch (SignatureException e) {
      LOG.error("Invalid JWT signature");
    } catch (MalformedJwtException e) {
      LOG.error("Invalid JWT token");
    } catch (ExpiredJwtException e) {
      LOG.error("Expired JWT token");
    } catch (UnsupportedJwtException e) {
      LOG.error("Unsupported JWT token");
    } catch (IllegalArgumentException e) {
      LOG.error("JWT claims string is empty.");
    }

    return false;
  }
}
