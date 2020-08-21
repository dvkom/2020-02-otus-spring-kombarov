package ru.dvkombarov.app.rest.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.dvkombarov.app.exceptions.ReportParseException;
import ru.dvkombarov.app.rest.dto.VulnerInfoDto;
import ru.dvkombarov.app.service.ReportAnalyzer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/api")
public class AnalyzeController {

  private final Logger LOG = LoggerFactory.getLogger(AnalyzeController.class);
  private static final String INTERNAL_ERROR_MESSAGE = "An error occurred while processing " +
      "the request on the server";

  private final ReportAnalyzer reportAnalyzer;

  public AnalyzeController(ReportAnalyzer reportAnalyzer) {
    this.reportAnalyzer = reportAnalyzer;
  }

  @PostMapping("/upload")
  public List<VulnerInfoDto> uploadFile(@RequestParam("file") MultipartFile file) {

    LOG.info("Call endpoint uploadFile, name = {}", file.getName());
    try (InputStream inputStream = file.getInputStream()) {
      return reportAnalyzer.analyze(inputStream);
    } catch (IOException e) {
      throw new ReportParseException(e);
    }
  }

  @ExceptionHandler(ReportParseException.class)
  public ResponseEntity<String> handleReportParseException(ReportParseException e) {
    LOG.error(e.getMessage(), e);

    return ResponseEntity.badRequest()
        .contentType(MediaType.APPLICATION_JSON)
        .body(e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleAnotherException(Exception e) {
    LOG.error(INTERNAL_ERROR_MESSAGE, e);

    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_JSON)
        .body(INTERNAL_ERROR_MESSAGE);
  }
}
