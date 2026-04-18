package dev.eckler.cashflow.domain.category;

import dev.eckler.cashflow.pivot.CategoryMonthRow;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("FROM Category c JOIN FETCH c.identifier WHERE c.userID = :userID")
    List<Category> findAllByUserID(@Param("userID") String userID);

    Optional<Category> findByIdAndUserID(Long Id, String UserID);

    @Query(value = """
        SELECT
            CAST(EXTRACT(YEAR  FROM t.date) AS INTEGER) AS year,
            CAST(EXTRACT(MONTH FROM t.date) AS INTEGER) AS month,
            c.label      AS category,
            c.type       AS type,
            SUM(t.amount) AS total
        FROM Transaction t
        LEFT JOIN identifier i ON t.identifier_id = i.id
        LEFT JOIN category c ON i.category_id   = c.id
        WHERE t.user_id = :userId
          AND (c.type IS NULL OR (c.type <> 'IGNORE' AND c.type <> 'INCOME'))
        GROUP BY year, month, c.label, c.type
        ORDER BY year, month
        """, nativeQuery = true)
    List<CategoryMonthRow> getRawMonthlySummary(@Param("userId") String userId);
}
