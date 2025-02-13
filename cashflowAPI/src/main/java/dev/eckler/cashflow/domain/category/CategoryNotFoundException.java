package dev.eckler.cashflow.domain.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException{

  public CategoryNotFoundException(String userID){
    super("The user: " + userID + " dont have this category");
  }

}
