package com.teqtrue.sitlink.exceptions;

import org.springframework.http.HttpStatus;

public class RequestException extends RuntimeException {
  private HttpStatus status;

  public RequestException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
