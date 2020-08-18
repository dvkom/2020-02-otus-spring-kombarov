package ru.dvkombarov.app.service;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import ru.dvkombarov.app.exceptions.ReportParseException;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ReportParserTikaImpl implements ReportParser {

  private final Logger LOG = LoggerFactory.getLogger(ReportParserTikaImpl.class);
  private static final Pattern CVE_PATTERN = Pattern.compile(
      "(CVE-(1999|2\\d{3})-(0\\d{2}[0-9]|[1-9]\\d{3,}))"
  );

  @Override
  @Nonnull
  public List<String> parseCve(InputStream inputStream) {
    try {
      String plainTextReport = parsePlainText(inputStream);
      Matcher matcher = CVE_PATTERN.matcher(plainTextReport);

      return matcher.results()
          .map(MatchResult::group)
          .distinct()
          .collect(Collectors.toList());
    } catch (Exception e) {
      LOG.error("Error parsing file:", e);
      throw new ReportParseException("Error parsing report");
    }
  }

  private static String parsePlainText(InputStream stream)
      throws IOException, TikaException, SAXException {

    Parser parser = new AutoDetectParser();
    ContentHandler handler = new BodyContentHandler();

    parser.parse(stream, handler, new Metadata(), new ParseContext());

    return handler.toString();
  }
}
