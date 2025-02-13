package dev.eckler.cashflow.domain.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import dev.eckler.cashflow.domain.identifier.Identifier;
import dev.eckler.cashflow.domain.identifier.IdentifierRepository;
import dev.eckler.cashflow.openapi.model.TransactionType;

@ActiveProfiles("dev")
@DataJpaTest(showSql = false)
public class CategoryRepositoryTest {

  @Autowired
  CategoryRepository categoryRepository;
  @Autowired
  IdentifierRepository identifierRepository;

  @Test
  void deleteCategory_ShouldAlsoDeleteAssociatedIdentifier() {
    Category category = getCategory();
    category = categoryRepository.save(category);
    assertEquals(1, categoryRepository.findAll().size());
    assertTrue(identifierRepository.findAll().size() == 1);

    category = createIdentifierForCategory(category);
    assertTrue(identifierRepository.findAll().size() == 3);

    categoryRepository.deleteById(category.getId());
    assertTrue(categoryRepository.findAll().size() == 0);
    assertTrue(identifierRepository.findAll().size() == 0);
  }

  @Test
  void createCategory_ShouldCreateUndefinedIdentifier() {
    Category category = getCategory();
    category = categoryRepository.save(category);
    assertEquals(1, categoryRepository.findAll().size());
    assertTrue(identifierRepository.findAll().size() == 1);
    Identifier identifier = identifierRepository.findAll().get(0);
    assertTrue(StringUtils.equals(identifier.getLabel(), dev.eckler.cashflow.shared.CashflowConst.UNDEFINED)
        && StringUtils.equals(identifier.getCategory().getId().toString(), category.getId().toString()));
  }

  private Category getCategory() {
    return new Category("category", "userGojo");
  }

  private Category createIdentifierForCategory(Category category) {
    Set<Identifier> identifiers = category.getIdentifier();
    identifiers.add(new Identifier("handsome", category));
    identifiers.add(new Identifier("foo", category));
    category.setIdentifier(identifiers);
    return categoryRepository.save(category);
  }

}
