package ru.dvkombarov.app.rest.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.dvkombarov.app.configuration.ReportAnalyzerConfig;
import ru.dvkombarov.app.rest.dto.VulnerInfoDto;
import ru.dvkombarov.app.service.VulnersService;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class VulnersServiceRestClientImpl implements VulnersService {

  private final Logger LOG = LoggerFactory.getLogger(VulnersServiceRestClientImpl.class);

  private final ReportAnalyzerConfig config;
  private RestOperations rest = new RestTemplate();

  public VulnersServiceRestClientImpl(ReportAnalyzerConfig config) {
    this.config = config;
  }

  @Override
  @Retryable(backoff=@Backoff(delay = 3000))
  public List<VulnerInfoDto> getVulnersInfoByCveList(@Nonnull List<String> cveList) {
    LOG.info("Call method getVulnersInfoByCveList, CVE count = {}", cveList.size());
    String requestParams = String.join(",", cveList);
    List<VulnerInfoDto> vulnerInfoDtoList = sendRestRequest(requestParams);
    LOG.info("Received VulnerInfoDto count = {}", vulnerInfoDtoList.size());

    return vulnerInfoDtoList;
  }

  private List<VulnerInfoDto> sendRestRequest(String requestParams) {
    RequestEntity request = new RequestEntity(
        HttpMethod.GET,
        UriComponentsBuilder.fromHttpUrl(config.getExploitSourceUrl())
            .path(config.getExploitSourceEndpoint() + requestParams)
            .queryParam("locale", "ru")
            .build().toUri());

    ResponseEntity<List<VulnerInfoDto>> response = rest.exchange(
        request, new ParameterizedTypeReference<>() {}
    );

    return Optional.ofNullable(response.getBody())
        .orElse(Collections.emptyList());
  }
}
