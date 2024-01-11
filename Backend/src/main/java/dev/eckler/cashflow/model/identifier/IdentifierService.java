package dev.eckler.cashflow.model.identifier;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class IdentifierService {

    private final IdentifierRepository identifierRepository;

    public IdentifierService(IdentifierRepository identifierRepository){
      this.identifierRepository = identifierRepository;
    }

    public List<Identifier> getAllIdentifier(){
      return identifierRepository.findAll();
    }

}
