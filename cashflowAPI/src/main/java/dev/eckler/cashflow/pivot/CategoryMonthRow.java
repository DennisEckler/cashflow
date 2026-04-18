package dev.eckler.cashflow.pivot;

import java.math.BigDecimal;

public record CategoryMonthRow(
    Integer year,
    Integer month,
    String category,
    String type,
    BigDecimal total
) {}