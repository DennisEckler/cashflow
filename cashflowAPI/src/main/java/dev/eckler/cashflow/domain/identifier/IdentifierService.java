package dev.eckler.cashflow.domain.identifier;

import static dev.eckler.cashflow.shared.CashflowConst.UNDEFINED;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import dev.eckler.cashflow.domain.category.CategoryRepository;

@Service
public class IdentifierService {

  private final IdentifierRepository ir;
  private final CategoryRepository categoryRepository;

  public IdentifierService(IdentifierRepository ir,
      CategoryRepository categoryRepository) {
    this.ir = ir;
    this.categoryRepository = categoryRepository;
  }

  public void deleteIdentifier(Long identiferID, Long categoryID, String userID) {
    Identifier i = ir.findById(identiferID).orElseThrow(
        () -> new IdentifierNotFoundException());
    if (isValidDeleteRequest(i, categoryID, userID)){
      ir.delete(i);
    } else {
      throw new UndefinedIdentifierIsNotAllowedToDelete();
    }
  }

  public boolean isValidDeleteRequest(Identifier i, Long categoryID, String userID){
    return StringUtils.equalsIgnoreCase(i.getCategory().getUserID(), userID)
      &&  i.getCategory().getId().equals(categoryID)
      && !StringUtils.equalsIgnoreCase(i.getLabel(), UNDEFINED);
  }

  public Identifier findIdentifierByID(Long identifierID) {
    return ir.findById(identifierID).orElse(null);
  }

  //
  //public ResponseEntity<Identifier> createIdentifier(IdentifierCreateRequest , String userID) {
  //  Identifier response = null;
  //  Optional<Category> category = categoryRepository.findById(
  //      Long.parseLong(identifierDTO.categoryID()));
  //  if (category.isPresent() && category.get().getUserID().equals(userID)) {
  //    Identifier identifier = new Identifier(identifierDTO.label(), category.get());
  //    response = ir.save(identifier);
  //  }
  //  return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
  //}
}
