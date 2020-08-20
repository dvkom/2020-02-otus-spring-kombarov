package ru.dvkombarov.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.dvkombarov.app.rest.dto.VulnerInfoDto;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Service
public class ReportAnalyzerImpl implements ReportAnalyzer {
  private final Logger LOG = LoggerFactory.getLogger(ReportAnalyzerImpl.class);

  private final ReportParser reportParser;
  private final VulnersService vulnersService;

  public ReportAnalyzerImpl(ReportParser reportParser,
                            VulnersService vulnersService) {
    this.reportParser = reportParser;
    this.vulnersService = vulnersService;
  }

  @Override
  public List<VulnerInfoDto> analyze(InputStream inputStream) {
    LOG.info("Analyze started");
    List<String> cveList = reportParser.parseCve(inputStream);
    LOG.info("Parsed cve's: {}", cveList);

    return cveList.isEmpty() ?
        Collections.emptyList() :
        vulnersService.getVulnersInfoByCveList(cveList);
  }
}
