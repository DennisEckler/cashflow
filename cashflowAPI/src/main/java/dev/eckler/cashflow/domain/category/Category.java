package dev.eckler.cashflow.domain.category;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import dev.eckler.cashflow.domain.identifier.Identifier;
import dev.eckler.cashflow.openapi.model.TransactionType;
import dev.eckler.cashflow.shared.CashflowConst;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "label", unique = true, nullable = false)
  private String label;

  @Column(name = "user_id", nullable = false)
  private String userID;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "type", nullable = false)
  private TransactionType type;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  private Set<Identifier> identifier;

  public Category() {
  }

  public Category(String label, String userID ) {
    this.label = label;
    this.userID = userID;
    this.type = TransactionType.FIXED;
    this.identifier = new HashSet<>(Arrays.asList(new Identifier(CashflowConst.UNDEFINED, this)));
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public TransactionType getType() {
    return type;
  }

  public void setType(TransactionType type) {
    this.type = type;
  }

  public Set<Identifier> getIdentifier() {
    return identifier;
  }

  public void setIdentifier(Set<Identifier> identifier) {
    this.identifier = identifier;
  }


}
