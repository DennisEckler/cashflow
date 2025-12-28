package dev.eckler.cashflow.domain.overview;

import java.math.BigDecimal;

public interface OverviewEntry {
    String getYear();

    String getMonth();

    String getType();

    BigDecimal getAmount();

}
