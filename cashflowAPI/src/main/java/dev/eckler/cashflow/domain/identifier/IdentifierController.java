package dev.eckler.cashflow.domain.identifier;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.eckler.cashflow.domain.util.JwtUtil;
import dev.eckler.cashflow.openapi.api.IdentifierApi;
import dev.eckler.cashflow.openapi.model.CashflowErrorResponse;
import dev.eckler.cashflow.openapi.model.IdentifierCreateRequest;
import dev.eckler.cashflow.openapi.model.IdentifierResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(path = "/v1/api")
@Log4j2
public class IdentifierController implements IdentifierApi {

  IdentifierService identifierService;
  private final JwtUtil jwtUtil;

  public IdentifierController(IdentifierService identifierService, JwtUtil jwtUtil) {
    this.identifierService = identifierService;
    this.jwtUtil = jwtUtil;
  }

  @ExceptionHandler({ IdentifierNotFoundException.class })
  public ResponseEntity<CashflowErrorResponse> error(IdentifierNotFoundException ex) {
    CashflowErrorResponse error = new CashflowErrorResponse();
    error.setStatusCode(HttpStatus.NOT_FOUND.value());
    error.setMessage(ex.getMessage());
    return ResponseEntity.status(error.getStatusCode()).body(error);
  }

  @Override
  public ResponseEntity<Void> deleteIdentifier(Long categoryID, Long identifierID) {
    String userID = jwtUtil.readSubjectFromSecurityContext();
    log.debug("Delete identifier: {} for category: {} as user: {}", identifierID, categoryID, userID);
    identifierService.deleteIdentifier(identifierID, categoryID, userID);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<IdentifierResponse> addIdentifier(Long categoryID,
      @Valid IdentifierCreateRequest identifierCreateRequest) {
    String userID = jwtUtil.readSubjectFromSecurityContext();
    log.debug("Create Identifier as user: {}", userID);
    IdentifierResponse identifierResponse = identifierService.addIdentifier(identifierCreateRequest, userID);
    return ResponseEntity.status(HttpStatus.CREATED).body(identifierResponse);
  }

}
