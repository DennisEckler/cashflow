package dev.eckler.cashflow.domain.overview;

import java.math.BigDecimal;

import dev.eckler.cashflow.openapi.model.TransactionType;

public record Overview(String year, String month, TransactionType type, BigDecimal amount) {

}
