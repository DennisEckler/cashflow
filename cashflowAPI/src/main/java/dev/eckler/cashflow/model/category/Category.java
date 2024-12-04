package dev.eckler.cashflow.model.category;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.eckler.cashflow.model.identifier.Identifier;
import dev.eckler.cashflow.shared.TransactionType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {

  public Category() {
  }

  public Category(String label, String userID, TransactionType type) {
    this.label = label;
    this.userID = userID;
    this.type = type;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String label;

  @NotNull
  private String userID;

  @NotNull
  @Enumerated(value = EnumType.STRING)
  private TransactionType type;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  private Set<Identifier> identifier;

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

  @JsonManagedReference
  public Set<Identifier> getIdentifier() {
    return identifier;
  }

  public void setIdentifier(Set<Identifier> identifier) {
    this.identifier = identifier;
  }

}
