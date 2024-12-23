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

@RestController
@RequestMapping(path = "/api/identifier")
public class IdentifierController {

  IdentifierService identifierService;

  public IdentifierController(IdentifierService identifierService) {
    this.identifierService = identifierService;
  }

  @PostMapping("/")
  public ResponseEntity<Identifier> createIdentifier(
      @RequestBody IdentifierDTO identifierDTO, @AuthenticationPrincipal Jwt jwt) {
    String userID = jwt.getSubject();
    return identifierService.createIdentifier(identifierDTO, userID);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteIdentifier(
      @PathVariable(name = "id") Long id, @AuthenticationPrincipal Jwt jwt) {
    String userID = jwt.getSubject();
    return identifierService.deleteIdentifier(id, userID);
  }

}
