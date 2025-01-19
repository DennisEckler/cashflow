package dev.eckler.cashflow.domain.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query("FROM Category c JOIN FETCH c.identifier WHERE c.userID = :userID")
  List<Category> findAllByUserID(@Param("userID") String userID);

  Optional<Category> findByIdAndUserID(Long Id, String UserID);
}
