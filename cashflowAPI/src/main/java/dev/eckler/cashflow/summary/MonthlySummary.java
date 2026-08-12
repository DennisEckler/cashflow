package dev.eckler.cashflow.summary;

import java.math.BigDecimal;
import java.util.Map;

public record MonthlySummary(
    int year,
    int month,
    Map<String, BigDecimal> categoryTotals,
    BigDecimal totalExpenses
) {
    public BigDecimal get(String category) {
        return categoryTotals.getOrDefault(category, BigDecimal.ZERO);
    }
}