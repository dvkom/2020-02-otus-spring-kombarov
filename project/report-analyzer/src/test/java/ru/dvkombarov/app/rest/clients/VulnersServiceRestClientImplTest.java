package ru.dvkombarov.app.rest.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import ru.dvkombarov.app.configuration.ReportAnalyzerConfig;
import ru.dvkombarov.app.rest.dto.ExploitDto;
import ru.dvkombarov.app.rest.dto.VulnerInfoDto;
import ru.dvkombarov.app.service.VulnersService;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(VulnersServiceRestClientImpl.class)
@Import(ReportAnalyzerConfig.class)
@DisplayName("Methods of the rest client should ")
class VulnersServiceRestClientImplTest {

  @Autowired
  private ReportAnalyzerConfig config;

  @Autowired
  private MockRestServiceServer server;

  @Autowired
  private VulnersService client;

  @Autowired
  private ObjectMapper objectMapper;


  @Test
  @DisplayName("return a list of vulnerabilities by request to endpoint ")
  void whenCallingGetUserDetails_thenClientMakesCorrectCall() throws JsonProcessingException {
    String cve = "cve1";
    String detailsString = objectMapper.writeValueAsString(List.of(
        new VulnerInfoDto(cve, "descr", Set.of(new ExploitDto()))
    ));

    String requestURL = config.getExploitSourceUrl() + config.getExploitSourceEndpoint() + cve;
    server.expect(requestTo(requestURL))
        .andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON));
    List<VulnerInfoDto> expected = List.of(
        new VulnerInfoDto("cve1", "descr", Set.of(new ExploitDto()))
    );
    List<VulnerInfoDto> vulnerInfoDtoList = this.client.getVulnersInfoByCveList(List.of("cve1"));
    assertThat(vulnerInfoDtoList).isEqualTo(expected);
  }
}