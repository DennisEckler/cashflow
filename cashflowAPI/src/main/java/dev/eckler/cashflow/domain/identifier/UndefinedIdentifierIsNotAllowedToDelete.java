package dev.eckler.cashflow.domain.identifier;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UndefinedIdentifierIsNotAllowedToDelete extends RuntimeException{

}
