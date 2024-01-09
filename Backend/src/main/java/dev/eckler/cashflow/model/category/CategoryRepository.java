package dev.eckler.cashflow.model.category;

import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  List<Category> findAllByUserID(String userID);
  Category findByLabel(String label);

  @Query("SELECT c FROM Category c JOIN FETCH c.identifier")
  List<Category> findAllCategories();


}
