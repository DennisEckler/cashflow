package dev.eckler.cashflow.summary;

import java.math.BigDecimal;

public record CategoryMonthRow(
    Integer year,
    Integer month,
    String category,
    String type,
    BigDecimal total
) {}