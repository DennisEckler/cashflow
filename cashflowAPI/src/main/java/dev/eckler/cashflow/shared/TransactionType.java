package dev.eckler.cashflow.shared;


public enum TransactionType {
  FIXED("Fixed"),
  VARIABLE("Variable"),
  UNIQUE("Unique");

  private String name;

  TransactionType(String name){
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
