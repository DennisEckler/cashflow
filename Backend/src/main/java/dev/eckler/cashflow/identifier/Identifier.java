package dev.eckler.cashflow.identifier;

import dev.eckler.cashflow.category.Category;
import dev.eckler.cashflow.transaktion.Transaktion;
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

  public Identifier(String label, Category category) {
    this.label = label;
    this.category = category;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String label;

  @OneToMany(mappedBy = "identifier")
  private Set<Transaktion> transaktions;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

}
