package ru.dvkombarov.app.service;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.util.List;

public interface ReportParser {
  @Nonnull
  List<String> parseCve(InputStream inputStream);
}
