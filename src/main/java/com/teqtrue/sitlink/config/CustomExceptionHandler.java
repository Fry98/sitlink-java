package com.teqtrue.sitlink.config;

import com.teqtrue.sitlink.exceptions.RequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
  
  @ExceptionHandler(RequestException.class)
  public final ResponseEntity<String> handleNotFoundException(RequestException ex) {
    return new ResponseEntity<String>(ex.getMessage(), ex.getStatus());
  }
}
