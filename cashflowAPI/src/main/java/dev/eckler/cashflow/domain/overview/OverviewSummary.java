package dev.eckler.cashflow.domain.overview;

import java.math.BigDecimal;

public class OverviewSummary {
    public OverviewSummary() {
    }

    private String year;
    private String month;
    private BigDecimal fixed = BigDecimal.ZERO;
    private BigDecimal variable = BigDecimal.ZERO;
    private BigDecimal income = BigDecimal.ZERO;
    private BigDecimal unique = BigDecimal.ZERO;
    private BigDecimal diff = BigDecimal.ZERO;

    void accumulateAmount(Overview row) {
        switch (row.type()) {
            case FIXED -> fixed = fixed.add(row.amount());
            case VARIABLE -> variable = variable.add(row.amount());
            case INCOME -> income = income.add(row.amount());
            case UNIQUE -> unique = unique.add(row.amount());
        }
        diff = diff.add(row.amount());
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getFixed() {
        return fixed;
    }

    public void setFixed(BigDecimal fixed) {
        this.fixed = fixed;
    }

    public BigDecimal getVariable() {
        return variable;
    }

    public void setVariable(BigDecimal variable) {
        this.variable = variable;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getUnique() {
        return unique;
    }

    public void setUnique(BigDecimal unique) {
        this.unique = unique;
    }

    public BigDecimal getDiff() {
        return diff;
    }

    public void setDiff(BigDecimal diff) {
        this.diff = diff;
    }

}
