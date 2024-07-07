package dev.eckler.cashflow.shared;

public record FileStructure(int dateIdx, int amountIdx, int purposeIdx, int sourceIdx,
                            String year, String month) {
}

