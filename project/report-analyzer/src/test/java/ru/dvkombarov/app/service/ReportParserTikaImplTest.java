package ru.dvkombarov.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.dvkombarov.app.exceptions.ReportParseException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("Methods of the report parser service should ")
class ReportParserTikaImplTest {

  @Autowired
  private ReportParserTikaImpl reportParserTika;

  // for properly context loading
  @MockBean
  private VulnersService vulnersService;

  @Test
  @DisplayName("return a list of cve if there are in the stream ")
  void shouldReturnCveListByStream() {
    String securityReport = "You have some problems with security, e.g. CVE-2009-4767,CVE-2009-4097." +
        "May be even CVE-2009-4096. And duplicate CVE-2009-4767 too. P.S. CVE-2000-BLABLA";
    InputStream inputStream = new ByteArrayInputStream(securityReport.getBytes());
    List<String> cveList = reportParserTika.parseCve(inputStream);
    assertThat(cveList).containsOnly("CVE-2009-4767", "CVE-2009-4097", "CVE-2009-4096");
  }

  @Test
  @DisplayName("return a list of cve if there are in the stream ")
  void shouldThrowExceptionWhenStreamInvalid() {
    assertThrows(ReportParseException.class, () -> reportParserTika.parseCve(null));
  }
}