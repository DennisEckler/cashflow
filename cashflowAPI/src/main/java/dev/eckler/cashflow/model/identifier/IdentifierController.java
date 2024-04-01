package dev.eckler.cashflow.model.identifier;

import static dev.eckler.cashflow.shared.CashflowConst.USER_ID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/identifier")
@CrossOrigin(origins = "http://localhost:4200")
public class IdentifierController {

  IdentifierService identifierService;

  public IdentifierController(IdentifierService identifierService) {
    this.identifierService = identifierService;
  }

  @PostMapping("/")
  public ResponseEntity<Identifier> createIdentifier(
      @RequestBody IdentifierDTO identifierDTO) {
    return identifierService.createIdentifier(identifierDTO, USER_ID);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteIdentifier(
      @PathVariable(name = "id") Long id) {
    return identifierService.deleteIdentifier(id, USER_ID);
  }

}
