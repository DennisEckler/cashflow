package dev.eckler.cashflow.model.category;

import dev.eckler.cashflow.model.identifier.Identifier;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "Category")
public class Category {

  public Category() {
  }

  public Category(String label, String userID) {
    this.label = label;
    this.userID = userID;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long categoryid;

  @NotNull
  private String label;
  @NotNull
  private String userID;

  @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
  private Set<Identifier> identifier;

  public Long getId() {
    return categoryid;
  }

  public void setId(Long id) {
    this.categoryid = id;
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

  public Set<Identifier> getIdentifier() {
    return identifier;
  }

  public void setIdentifier(Set<Identifier> identifier) {
    this.identifier = identifier;
  }
}
