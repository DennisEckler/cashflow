package dev.eckler.cashflow.model.identifier;

import static dev.eckler.cashflow.jwt.CustomJwt.getUserId;

import dev.eckler.cashflow.config.Oauth2Properties;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/identifier")
@CrossOrigin(origins = "http://localhost:4200")
public class IdentifierController {

  IdentifierService identifierService;
  Oauth2Properties oauthProperties;

  public IdentifierController(IdentifierService identifierService, Oauth2Properties oauthProperties){
    this.identifierService = identifierService;
    this.oauthProperties = oauthProperties;
  }

  @PostMapping("/")
  public ResponseEntity<Identifier> createIdentifier(
      @RequestHeader("Authorization") String bearerRequest,
      @RequestBody IdentifierDTO identifierDTO){
    String userID = getUserId(bearerRequest, oauthProperties.issuerUri());
    return identifierService.createIdentifier(identifierDTO, userID);
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteIdentifier(
      @RequestHeader("Authorization") String bearerRequest,
      @PathVariable(name = "id") Long id){
    String userID = getUserId(bearerRequest, oauthProperties.issuerUri());
    return identifierService.deleteIdentifier(id, userID);
  }

}
