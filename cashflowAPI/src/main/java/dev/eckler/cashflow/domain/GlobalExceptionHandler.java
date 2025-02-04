package dev.eckler.cashflow.domain;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.eckler.cashflow.openapi.model.CashflowErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler{
  

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<CashflowErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
    CashflowErrorResponse error = new CashflowErrorResponse();
    StringBuilder sb = new StringBuilder();
    error.setStatusCode(ex.getStatusCode().value());
    ex.getBindingResult().getFieldErrors().forEach( fieldError ->{
      sb.append(fieldError.getField() + ": " + fieldError.getDefaultMessage() + "\n");
    });
    return ResponseEntity.badRequest().body(error);
  }

}
