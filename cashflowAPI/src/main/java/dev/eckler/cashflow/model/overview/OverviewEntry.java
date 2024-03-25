package dev.eckler.cashflow.model.overview;

import java.math.BigDecimal;

public interface OverviewEntry {
  String getYear();

  String getMonth();

  String getType();

  BigDecimal getAmount();

}
