package dev.eckler.cashflow.model.category;

import dev.eckler.cashflow.model.identifier.Identifier;
import jakarta.persistence.Column;
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
@Table(name = "category")
public class Category {

  public Category() {
  }

  public Category(String categoryLabel, String userID) {
    this.categoryLabel = categoryLabel;
    this.userID = userID;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long categoryID;

  @NotNull
  @Column(name = "CATEGORYLABEL")
  private String categoryLabel;
  @NotNull
  private String userID;

  @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
  private Set<Identifier> identifier;

  public Long getCategoryID() {
    return categoryID;
  }

  public void setCategoryID(Long id) {
    this.categoryID = id;
  }

  public String getCategoryLabel() {
    return categoryLabel;
  }

  public void setCategoryLabel(String category) {
    this.categoryLabel = category;
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
