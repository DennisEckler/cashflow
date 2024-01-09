package dev.eckler.cashflow.model.transaktion;

import dev.eckler.cashflow.model.identifier.Identifier;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TransaktionRepository extends JpaRepository<Transaktion, Long> {

  List<Transaktion> findAllByIdentifier(Identifier identifier);

  List<Transaktion> findAllByIdentifierNot(Identifier identifier);

  @Query("select count(t) from Transaktion t WHERE YEAR(t.date) = :year AND MONTH(t.date) = :month")
  int getNumberOfYearMonthMatches(String year, String month);

//  @Query("SELECT YEAR(t.date) AS year, MONTH(t.date) AS month, t.category AS category, SUM(t.amount) AS amount FROM Transaktion AS t GROUP By year, month, category ORDER By year, month, category")
//  List<OverviewEntry> getOverview();

}
