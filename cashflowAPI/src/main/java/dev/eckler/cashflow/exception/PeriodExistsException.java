package dev.eckler.cashflow.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PeriodExistsException extends Exception {

  public PeriodExistsException(String message) {
    super(message);
  }

}
