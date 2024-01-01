package dev.eckler.cashflow.category;

import java.util.Set;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

  Set<Category> findAllByUserID(String userID);

}
