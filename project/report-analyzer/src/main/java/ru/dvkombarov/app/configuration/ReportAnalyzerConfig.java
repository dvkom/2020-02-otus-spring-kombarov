package ru.dvkombarov.app.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class ReportAnalyzerConfig {
  private String exploitSourceUrl;
  private String exploitSourceEndpoint;

  public String getExploitSourceUrl() {
    return exploitSourceUrl;
  }

  public void setExploitSourceUrl(String exploitSourceUrl) {
    this.exploitSourceUrl = exploitSourceUrl;
  }

  public String getExploitSourceEndpoint() {
    return exploitSourceEndpoint;
  }

  public void setExploitSourceEndpoint(String exploitSourceEndpoint) {
    this.exploitSourceEndpoint = exploitSourceEndpoint;
  }
}
