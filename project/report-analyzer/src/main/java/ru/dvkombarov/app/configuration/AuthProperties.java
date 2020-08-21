package ru.dvkombarov.app.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.auth")
public class AuthProperties {
  private String tokenSecret;
  private long tokenExpirationMsec;
  private String tokenType;

  public String getTokenSecret() {
    return tokenSecret;
  }

  public void setTokenSecret(String tokenSecret) {
    this.tokenSecret = tokenSecret;
  }

  public long getTokenExpirationMsec() {
    return tokenExpirationMsec;
  }

  public void setTokenExpirationMsec(long tokenExpirationMsec) {
    this.tokenExpirationMsec = tokenExpirationMsec;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }
}
