package dev.eckler.cashflow.domain.identifier;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.eckler.cashflow.domain.category.Category;
import dev.eckler.cashflow.domain.transaction.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Identifier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "label", unique = true, nullable = false)
    private String label;

    @OneToMany(mappedBy = "identifier")
    private Set<Transaction> transactions;

    @ManyToOne
    private Category category;

    public Identifier() {
    }

    public Identifier(String label, Category category) {
        this.label = label;
        this.category = category;
    }

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

    @Override
    public String toString() {
        return "Identifier [id=" + id + ", label=" + label + "]";
    }
}
