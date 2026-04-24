package dev.eckler.cashflow.domain.category;

import dev.eckler.cashflow.pivot.CategoryMonthRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("FROM Category c JOIN FETCH c.identifier WHERE c.userID = :userID")
    List<Category> findAllByUserID(@Param("userID") String userID);

    Optional<Category> findByIdAndUserID(Long Id, String UserID);

    @Query(value = """
        SELECT
            CAST(EXTRACT(YEAR  FROM t.date) AS INTEGER) AS yr,
            CAST(EXTRACT(MONTH FROM t.date) AS INTEGER) AS mo,
            c.label      AS category,
            c.type       AS type,
            SUM(t.amount) AS total
        FROM Transaction t
        INNER JOIN identifier i ON t.identifier_id = i.id
        INNER JOIN category c ON i.category_id   = c.id
        WHERE t.user_id = :userId
          AND  c.type <> 'IGNORE' AND c.type <> 'INCOME'
        GROUP BY CAST(EXTRACT(YEAR  FROM t.date) AS INTEGER),
                 CAST(EXTRACT(MONTH FROM t.date) AS INTEGER),
                 c.label, c.type
        ORDER BY CAST(EXTRACT(YEAR  FROM t.date) AS INTEGER),
                 CAST(EXTRACT(MONTH FROM t.date) AS INTEGER)
        """, nativeQuery = true)
    List<CategoryMonthRow> getMonthlyTotalExpensesPerCategory(@Param("userId") String userId);
}
