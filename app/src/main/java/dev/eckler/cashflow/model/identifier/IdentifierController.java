package dev.eckler.cashflow.model.identifier;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/identifier")
@CrossOrigin(origins = "http://localhost:4200")
public class IdentifierController {

  IdentifierService identifierService;

  public IdentifierController(IdentifierService identifierService){
    this.identifierService = identifierService;
  }

  @GetMapping("/get")
  public List<Identifier> getAllIdentifier(){
    return this.identifierService.getAllIdentifier();
  }

  @DeleteMapping("/delete/{identifierID}")
  public ResponseEntity<String> deleteIdentifier(@PathVariable(name = "identifierID") Long identifierID){
    if (identifierService.deleteIdentifier(identifierID)){
      return new ResponseEntity<>("Deleted Identifier", HttpStatus.OK);
    }
    return new ResponseEntity<>("Cant find Identifer", HttpStatus.NOT_FOUND);
  }

}
