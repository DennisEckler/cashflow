package dev.eckler.cashflow.model.overview;

import dev.eckler.cashflow.shared.TransactionType;
import java.math.BigDecimal;

public record Overview(String year, String month, TransactionType type, BigDecimal amount) {

}
