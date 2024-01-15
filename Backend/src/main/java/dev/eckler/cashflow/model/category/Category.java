package dev.eckler.cashflow.model.category;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.eckler.cashflow.model.identifier.Identifier;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Entity
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
  private String categoryLabel;
  @NotNull
  private String userID;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
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

  @JsonManagedReference
  public Set<Identifier> getIdentifier() {
    return identifier;
  }

  public void setIdentifier(Set<Identifier> identifier) {
    this.identifier = identifier;
  }
}
