package dev.eckler.cashflow.model.category;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query("FROM Category c JOIN FETCH c.identifier WHERE c.userID = :userID")
  List<Category> findAllByUserID(@Param("userID") String userID);


  Category findByLabel(String label);



}
