package dev.eckler.cashflow.domain.identifier;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.eckler.cashflow.domain.util.JwtUtil;
import dev.eckler.cashflow.openapi.api.IdentifierApi;
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

  //@Override
  //public ResponseEntity<IdentifierResponse> addIdentifier(Long categoryID,
  //    @Valid IdentifierCreateRequest identifierCreateRequest) {
  //  String userID = jwtUtil.readSubjectFromSecurityContext();
  //  log.debug("Create Identifier as user: {}", userID);
  //  return identifierService.createIdentifier(identifierCreateRequest, userID);
  //}

  @Override
  public ResponseEntity<Void> deleteIdentifier(Long categoryID, Long identifierID) {
    String userID = jwtUtil.readSubjectFromSecurityContext();
    log.debug("Delete identifier: {} for category: {} as user: {}", identifierID, categoryID, userID);
    identifierService.deleteIdentifier(identifierID, categoryID, userID);
    return ResponseEntity.noContent().build();
  }
  
}
