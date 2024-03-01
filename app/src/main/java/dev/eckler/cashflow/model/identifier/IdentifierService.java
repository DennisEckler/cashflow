package dev.eckler.cashflow.model.identifier;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class IdentifierService {

  private final IdentifierRepository identifierRepository;

  public IdentifierService(IdentifierRepository identifierRepository) {
    this.identifierRepository = identifierRepository;
  }

  public List<Identifier> getAllIdentifier() {
    return identifierRepository.findAll();
  }

  public boolean deleteIdentifier(Long identifierID) {
    Optional<Identifier> identifier = identifierRepository.findById(identifierID);
    if (identifier.isPresent()) {
      identifier.get().getTransaktions().forEach(ele -> ele.setIdentifier(null));
      identifierRepository.deleteById(identifierID);
      return true;
    }
    return false;
  }


  public Identifier findIdentifierByID(Long identifierID){
    return identifierRepository.findById(identifierID).orElse(null);
  }



}
