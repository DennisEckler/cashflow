package dev.eckler.cashflow.domain.transaction;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.eckler.cashflow.domain.identifier.Identifier;
import dev.eckler.cashflow.domain.overview.OverviewEntry;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByIdentifierIsNullAndUserID(String userID);

    List<Transaction> findAllByIdentifier(Identifier identifier);

    @Query("SELECT YEAR(t.date) AS year, MONTH(t.date) AS month, c.type AS type, SUM(t.amount) AS amount "
            + "FROM Transaction t "
            + "JOIN t.identifier i "
            + "JOIN i.category c "
            + "WHERE t.identifier.id = i.id AND i.category.id = c.id AND c.userID = :userID "
            + "GROUP By year, month, c.type "
            + "ORDER By year, month")
    List<OverviewEntry> getOverview(String userID);

    List<Transaction> findAllByUserID(String userID);

    List<Transaction> findByDateBetween(LocalDate start, LocalDate end);
}
