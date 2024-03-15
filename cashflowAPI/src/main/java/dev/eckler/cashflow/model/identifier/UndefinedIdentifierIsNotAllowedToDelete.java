package dev.eckler.cashflow.model.identifier;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UndefinedIdentifierIsNotAllowedToDelete extends RuntimeException{

}
