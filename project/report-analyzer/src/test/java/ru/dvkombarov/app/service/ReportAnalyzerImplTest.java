package ru.dvkombarov.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.dvkombarov.app.exceptions.ReportParseException;
import ru.dvkombarov.app.rest.dto.VulnerInfoDto;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@DisplayName("Methods of the report analyze service should ")
class ReportAnalyzerImplTest {

  @MockBean
  private ReportParser reportParser;
  @MockBean
  private VulnersService vulnersService;
  @Autowired
  private ReportAnalyzerImpl reportAnalyzer;

  @Test
  @DisplayName("return a list of vulnerabilities if present in the stream ")
  void shouldReturnVulnersByStream() {
    List<VulnerInfoDto> expected = List.of(new VulnerInfoDto());
    List<String> cveList = List.of("cve1");
    doReturn(cveList).when(reportParser).parseCve(any(InputStream.class));
    doReturn(expected).when(vulnersService).getVulnersInfoByCveList(eq(cveList));

    List<VulnerInfoDto> actual = reportAnalyzer.analyze(new ByteArrayInputStream(new byte[]{}));
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  @DisplayName("return a empty list if there aren't cve's in the stream ")
  void shouldReturnEmptyListByStreamWithoutCves() {
    doReturn(Collections.emptyList()).when(reportParser).parseCve(any(InputStream.class));

    List<VulnerInfoDto> actual = reportAnalyzer.analyze(new ByteArrayInputStream(new byte[]{}));
    assertThat(actual).isEmpty();
  }

  @Test
  @DisplayName("not catch any exceptions ")
  void shouldNotCatchExceptions() {
    doThrow(new ReportParseException("error")).when(reportParser).parseCve(any(InputStream.class));

    assertThrows(ReportParseException.class,
        () -> reportAnalyzer.analyze(new ByteArrayInputStream(new byte[]{})));
  }
}