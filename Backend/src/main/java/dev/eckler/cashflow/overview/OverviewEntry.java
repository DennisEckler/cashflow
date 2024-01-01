package dev.eckler.cashflow.overview;

public interface OverviewEntry {
  String getYear();

  String getMonth();

  String getCategory();

  Float getAmount();

}
