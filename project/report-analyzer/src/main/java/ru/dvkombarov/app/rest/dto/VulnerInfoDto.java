package ru.dvkombarov.app.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VulnerInfoDto {

  private String cve;
  private String description;
  private Set<ExploitDto> exploits;

  public String getCve() {
    return cve;
  }

  public void setCve(String cve) {
    this.cve = cve;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<ExploitDto> getExploits() {
    return exploits;
  }

  public void setExploits(Set<ExploitDto> exploits) {
    this.exploits = exploits;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof VulnerInfoDto)) return false;
    VulnerInfoDto that = (VulnerInfoDto) o;
    return Objects.equals(cve, that.cve) &&
        Objects.equals(description, that.description) &&
        Objects.equals(exploits, that.exploits);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cve, description, exploits);
  }

  @Override
  public String toString() {
    return "VulnerInfoDto{" +
        "cve='" + cve + '\'' +
        ", description='" + description + '\'' +
        ", exploits=" + exploits +
        '}';
  }
}
