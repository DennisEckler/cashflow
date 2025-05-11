package dev.eckler.cashflow.domain.identifier;

import static dev.eckler.cashflow.shared.CashflowConst.UNDEFINED;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import dev.eckler.cashflow.domain.category.Category;
import dev.eckler.cashflow.domain.category.CategoryRepository;
import dev.eckler.cashflow.openapi.model.IdentifierCreateRequest;
import dev.eckler.cashflow.openapi.model.IdentifierResponse;

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
    if (isValidDeleteRequest(i, categoryID, userID)) {
      ir.delete(i);
    } else {
      throw new UndefinedIdentifierIsNotAllowedToDelete();
    }
  }

  public boolean isValidDeleteRequest(Identifier i, Long categoryID, String userID) {
    return StringUtils.equalsIgnoreCase(i.getCategory().getUserID(), userID)
        && i.getCategory().getId().equals(categoryID)
        && !StringUtils.equalsIgnoreCase(i.getLabel(), UNDEFINED);
  }

  public Identifier findIdentifierByID(Long identifierID) {
    return ir.findById(identifierID).orElse(null);
  }

  public IdentifierResponse addIdentifier(IdentifierCreateRequest identifierCreateRequest, String userID) {
    Optional<Category> category = categoryRepository.findById(identifierCreateRequest.getCategoryID());
    if (category.isPresent() && category.get().getUserID().equals(userID)) {
      Identifier identifier = ir.save(new Identifier(identifierCreateRequest.getLabel(), category.get()));
      IdentifierResponse identifierResponse = IdentifierMapper.identifierToIdentifierResponse(identifier);
      return identifierResponse;
    } else {
      throw new IdentifierNotFoundException();
    }
  }
}
