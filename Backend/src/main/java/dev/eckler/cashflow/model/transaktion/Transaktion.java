package dev.eckler.cashflow.model.transaktion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.eckler.cashflow.model.identifier.Identifier;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Transaktion")
public class Transaktion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long transaktionid;
  @NotNull
  private Date date;
  @NotNull
  private float amount;
  @NotNull
  private String userID;
  @NotNull
  private String purpose;
  @NotNull
  private String source;
  @ManyToOne
//  @JsonBackReference
  @JoinColumn(name = "identifierid")
  @JsonIgnore
  private Identifier identifier;

  public Transaktion(Date date, float amount, String userID, String purpose, String source,
      Identifier identifier) {
    this.date = date;
    this.amount = amount;
    this.userID = userID;
    this.source = source;
    this.purpose = purpose;
    this.identifier = identifier;
  }

  public Transaktion() {
  }

  public Long getTransaktionid() {
    return transaktionid;
  }

  public void setTransaktionid(Long transaktionid) {
    this.transaktionid = transaktionid;
  }

  public Identifier getIdentifier() {
    return identifier;
  }

  public void setIdentifier(Identifier identifier) {
    this.identifier = identifier;
  }

  public Long getId() {
    return transaktionid;
  }

  public void setId(Long id) {
    this.transaktionid = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public float getAmount() {
    return amount;
  }

  public void setAmount(float amount) {
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
