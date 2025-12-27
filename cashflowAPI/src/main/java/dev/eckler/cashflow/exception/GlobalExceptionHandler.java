package dev.eckler.cashflow.exception;

import java.time.OffsetDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.eckler.cashflow.openapi.model.CashflowErrorResponse;
import dev.eckler.cashflow.openapi.model.CashflowValidationErrorResponse;
import dev.eckler.cashflow.openapi.model.CashflowValidationErrorResponseSubErrorInner;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CashflowValidationErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex) {
        CashflowValidationErrorResponse error = new CashflowValidationErrorResponse();
        error.setStatusCode(ex.getStatusCode().value());
        error.setDatetime(OffsetDateTime.now());
        error.setMessage("Validation failed");
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            CashflowValidationErrorResponseSubErrorInner subError = new CashflowValidationErrorResponseSubErrorInner();
            subError.setField(fieldError.getField());
            subError.setFieldError(fieldError.getDefaultMessage()
                    .concat(" | for this rejected value: ")
                    .concat(String.valueOf(fieldError.getRejectedValue())));
            error.addSubErrorItem(subError);
        });

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CashflowErrorResponse> handleUniqueKeyConstraintException(
            DataIntegrityViolationException ex) {
        CashflowErrorResponse error = new CashflowErrorResponse();
        int statusCode = HttpStatus.CONFLICT.value();
        error.setStatusCode(statusCode);
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(statusCode).body(error);
    }

}
