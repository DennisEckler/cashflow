package dev.eckler.cashflow.domain.identifier;

import java.util.List;
import java.util.Set;

import dev.eckler.cashflow.openapi.model.IdentifierResponse;

public class IdentifierMapper{

  public static IdentifierResponse identifierToIdentifierResponse(Identifier identifier){
    IdentifierResponse identifierResponse = new IdentifierResponse();
    if (identifier != null){
      identifierResponse.setId(identifier.getId());
      identifierResponse.setLabel(identifier.getLabel());
    }
    return identifierResponse;
  }

  public static List<IdentifierResponse> identifierSetToList(Set<Identifier> identifierSet){
    return identifierSet.stream()
      .map(IdentifierMapper::identifierToIdentifierResponse)
      .toList();
  }
}
