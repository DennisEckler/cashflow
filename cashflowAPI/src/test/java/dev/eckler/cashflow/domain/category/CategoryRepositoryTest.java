package dev.eckler.cashflow.domain.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import dev.eckler.cashflow.domain.identifier.IdentifierRepository;

@DataJpaTest(showSql = false)
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    IdentifierRepository identifierRepository;

}
