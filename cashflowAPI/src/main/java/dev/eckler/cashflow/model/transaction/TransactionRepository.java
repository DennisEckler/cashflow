package dev.eckler.cashflow.model.transaction;

import dev.eckler.cashflow.model.identifier.Identifier;
import dev.eckler.cashflow.model.overview.OverviewEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findAllByIdentifierIsNull();

  List<Transaction> findAllByIdentifier(Identifier identifier);

  @Query("select count(t) from Transaction t WHERE YEAR(t.date) = :year AND MONTH(t.date) = :month")
  int getNumberOfYearMonthMatches(String year, String month);

  @Query(
      "SELECT YEAR(t.date) AS year, MONTH(t.date) AS month, c.type AS type, SUM(t.amount) AS amount "
          + "FROM Transaction t "
          + "JOIN t.identifier i "
          + "JOIN i.category c "
          + "WHERE t.identifier.id = i.id AND i.category.id = c.id AND c.userID = :userID "
          + "GROUP By year, month, c.type "
          + "ORDER By year, month")
  List<OverviewEntry> getOverview(String userID);
  
  
  List<Transaction> findAllByUserID(String userID);
}
