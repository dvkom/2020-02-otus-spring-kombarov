package ru.dvkombarov.app.exceptions;

public class ReportParseException extends RuntimeException {

  public ReportParseException(String message) {
    super(message);
  }

  public ReportParseException(Throwable cause) {
    super(cause);
  }
}
