package ru.dvkombarov.app.rest.dto;

public class SingUpResponse {
  private boolean isSuccess;
  private String message;

  public SingUpResponse(boolean isSuccess, String message) {
    this.isSuccess = isSuccess;
    this.message = message;
  }

  public boolean isSuccess() {
    return isSuccess;
  }

  public void setSuccess(boolean success) {
    this.isSuccess = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
