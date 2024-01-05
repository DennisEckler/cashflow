package dev.eckler.cashflow.model.transaktion.overview;

public interface OverviewEntry {
  String getYear();

  String getMonth();

  String getCategory();

  Float getAmount();

}
