package dev.eckler.cashflow.model.identifier;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.eckler.cashflow.model.category.Category;
import dev.eckler.cashflow.model.transaction.Transaction;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"category"})
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
    private Set<Transaction> transactions;
    
    @ManyToOne
    private Category category;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @JsonIgnore
    public Set<Transaction> getTransactions() {
        return transactions;
    }
    
    public void setTransactions(
        Set<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    @JsonBackReference
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }
    
}
