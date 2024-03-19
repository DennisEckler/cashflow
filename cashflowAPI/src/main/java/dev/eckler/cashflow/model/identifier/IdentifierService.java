package dev.eckler.cashflow.model.identifier;

import static dev.eckler.cashflow.shared.CashflowConst.UNDEFINED;

import dev.eckler.cashflow.model.category.Category;
import dev.eckler.cashflow.model.category.CategoryRepository;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class IdentifierService {

  private final IdentifierRepository identifierRepository;
  private final CategoryRepository categoryRepository;

  public IdentifierService(IdentifierRepository identifierRepository,
      CategoryRepository categoryRepository) {
    this.identifierRepository = identifierRepository;
    this.categoryRepository = categoryRepository;
  }

  public ResponseEntity<String> deleteIdentifier(Long identiferID, String userID) {
    Optional<Identifier> identifier = identifierRepository.findById(identiferID);
    if (identifier.isPresent() && identifier.get().getCategory().getUserID().equals(userID)) {
      return deleteIdentifier(identiferID, false);
    }
    throw new IdentifierNotFoundException();
  }

  public ResponseEntity<String> deleteIdentifier(Long identifierID,
      boolean isAllowedToDeleteUndefined) {
    Optional<Identifier> identifier = identifierRepository.findById(identifierID);
    if (identifier.isPresent() && (isAllowedToDeleteUndefined || !identifier.get()
        .getIdentifierLabel().equals(UNDEFINED))) {
      identifier.get().getTransactions().forEach(ele -> ele.setIdentifier(null));
      identifierRepository.deleteById(identifierID);
      return ResponseEntity.ok().build();
    }
    throw new UndefinedIdentifierIsNotAllowedToDelete();
  }


  public Identifier findIdentifierByID(Long identifierID) {
    return identifierRepository.findById(identifierID).orElse(null);
  }


  public ResponseEntity<Identifier> createIdentifier(IdentifierDTO identifierDTO, String userID) {
    Identifier response = null;
    Optional<Category> category = categoryRepository.findById(
        Long.parseLong(identifierDTO.categoryID()));
    if (category.isPresent() && category.get().getUserID().equals(userID)) {
      Identifier identifier = new Identifier(identifierDTO.identifierLabel(), category.get());
      response = identifierRepository.save(identifier);
    }
    return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
  }
}
