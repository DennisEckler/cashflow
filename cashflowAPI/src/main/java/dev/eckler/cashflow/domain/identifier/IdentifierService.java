package dev.eckler.cashflow.domain.identifier;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dev.eckler.cashflow.constants.CashflowConst;
import dev.eckler.cashflow.domain.category.Category;
import dev.eckler.cashflow.domain.category.CategoryRepository;
import dev.eckler.cashflow.openapi.model.IdentifierCreateRequest;
import dev.eckler.cashflow.openapi.model.IdentifierResponse;

@Service
public class IdentifierService {

    private final Logger logger = LoggerFactory.getLogger(IdentifierService.class);
    private final IdentifierRepository ir;
    private final CategoryRepository cr;

    public IdentifierService(IdentifierRepository ir,
            CategoryRepository cr) {
        this.ir = ir;
        this.cr = cr;
    }

    public void deleteIdentifier(Long identiferID, Long categoryID, String userID) {
        Identifier i = ir.findById(identiferID).orElseThrow(
                () -> new IdentifierNotFoundException());
        if (isValidDeleteRequest(i, categoryID, userID)) {
            logger.debug("Delete Identifier: " + i.toString());
            Category c = i.getCategory();
            c.getIdentifier().remove(i);
            ir.delete(i);
        } else {
            logger.debug("Is not Valid Delete Identifier Request");
            throw new UndefinedIdentifierIsNotAllowedToDelete();
        }
    }

    public boolean isValidDeleteRequest(Identifier i, Long categoryID, String userID) {
        return StringUtils.equalsIgnoreCase(i.getCategory().getUserID(), userID)
                && i.getCategory().getId().equals(categoryID)
                && !StringUtils.equalsIgnoreCase(i.getLabel(),
                        i.getCategory().getLabel() + "_" + CashflowConst.DEFAULT);
    }

    public Identifier findIdentifierByID(Long identifierID) {
        return ir.findById(identifierID).orElse(null);
    }

    public IdentifierResponse addIdentifier(IdentifierCreateRequest identifierCreateRequest, String userID) {
        Optional<Category> category = cr.findById(identifierCreateRequest.getCategoryID());
        if (category.isPresent() && category.get().getUserID().equals(userID)) {
            Identifier identifier = ir.save(new Identifier(identifierCreateRequest.getLabel(), category.get()));
            IdentifierResponse identifierResponse = IdentifierMapper.identifierToIdentifierResponse(identifier);
            return identifierResponse;
        } else {
            throw new IdentifierNotFoundException();
        }
    }
}
