package ru.dvkombarov.app.service;

import ru.dvkombarov.app.rest.dto.VulnerInfoDto;

import java.io.InputStream;
import java.util.List;

public interface ReportAnalyzer {
  List<VulnerInfoDto> analyze(InputStream inputStream);
}
