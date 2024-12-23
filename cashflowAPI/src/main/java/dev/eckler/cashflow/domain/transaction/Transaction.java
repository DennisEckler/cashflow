package dev.eckler.cashflow.domain.transaction;

import dev.eckler.cashflow.domain.identifier.Identifier;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  private LocalDate date;
  @NotNull
  @Digits(integer = 9, fraction = 2)
  private BigDecimal amount;
  @NotNull
  private String userID;
  @NotNull
  private String purpose;
  @NotNull
  private String source;

  @ManyToOne
  private Identifier identifier;

  public Transaction(LocalDate date, BigDecimal amount, String userID, String purpose, String source,
      Identifier identifier) {
    this.date = date;
    this.amount = amount;
    this.userID = userID;
    this.source = source;
    this.purpose = purpose;
    this.identifier = identifier;
  }

  public Transaction() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Identifier getIdentifier() {
    return identifier;
  }

  public void setIdentifier(Identifier identifier) {
    this.identifier = identifier;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public String getPurpose() {
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }
}
