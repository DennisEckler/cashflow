package dev.eckler.cashflow.model.identifier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.eckler.cashflow.model.category.Category;
import dev.eckler.cashflow.model.transaction.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "Identifier")
public class Identifier {


  public Identifier() {
  }

  public Identifier(String identifierLabel, Category category) {
    this.identifierLabel = identifierLabel;
    this.category = category;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long identifierID;

  @NotNull
  @Column(name = "IDENTIFIERLABEL")
  private String identifierLabel;

  @OneToMany(mappedBy = "identifier")
  @JsonIgnore
  private Set<Transaction> transactions;

  @ManyToOne
  @JoinColumn(name = "categoryID")
  @JsonIgnore
  private Category category;

  public Long getIdentifierID() {
    return identifierID;
  }

  public void setIdentifierID(Long identifierID) {
    this.identifierID = identifierID;
  }

  public Set<Transaction> getTransaktions() {
    return transactions;
  }

  public void setTransaktions(
      Set<Transaction> transactions) {
    this.transactions = transactions;
  }

  public String getIdentifierLabel() {
    return identifierLabel;
  }

  public void setIdentifierLabel(String identifierLabel) {
    this.identifierLabel = identifierLabel;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

}
