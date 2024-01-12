package dev.eckler.cashflow.model.transaction;

import dev.eckler.cashflow.model.identifier.Identifier;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findAllByIdentifier(Identifier identifier);

  List<Transaction> findAllByIdentifierNot(Identifier identifier);

  @Query("select count(t) from Transaction t WHERE YEAR(t.date) = :year AND MONTH(t.date) = :month")
  int getNumberOfYearMonthMatches(String year, String month);

//  @Query("SELECT YEAR(t.date) AS year, MONTH(t.date) AS month, t.category AS category, SUM(t.amount) AS amount FROM Transaction AS t GROUP By year, month, category ORDER By year, month, category")
//  List<OverviewEntry> getOverview();

}
