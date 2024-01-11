package dev.eckler.cashflow.model.identifier;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IdentifierController {

  IdentifierService identifierService;

  public IdentifierController(IdentifierService identifierService){
    this.identifierService = identifierService;
  }

  @GetMapping("/identifier")
  @PreAuthorize("hasAuthority('ROLE_user')")
  public List<Identifier> getAllIdentifier(){
    return this.identifierService.getAllIdentifier();
  }

}
