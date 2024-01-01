package dev.eckler.cashflow.category;

import dev.eckler.cashflow.identifier.Identifier;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
  private Long id;

  @NotNull
  private String label;
  @NotNull
  private String userID;

  @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
  private Set<Identifier> identifier;

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
}
