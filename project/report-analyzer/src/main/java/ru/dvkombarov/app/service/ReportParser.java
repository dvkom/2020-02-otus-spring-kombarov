package ru.dvkombarov.app.service;

import java.io.InputStream;
import java.util.List;

public interface ReportParser {
  List<String> parseCve(InputStream inputStream);
}
