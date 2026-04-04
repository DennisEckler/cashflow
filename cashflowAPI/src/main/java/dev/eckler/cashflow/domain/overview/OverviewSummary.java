package dev.eckler.cashflow.domain.overview;

import dev.eckler.cashflow.openapi.model.OverviewSummaryResponse;

public class OverviewSummary extends OverviewSummaryResponse {

    void accumulateAmount(Overview row) {
        switch (row.type()) {
            case FIXED -> setFixed(getFixed().add(row.amount()));
            case VARIABLE -> setVariable(getVariable().add(row.amount()));
            case INCOME -> setIncome(getIncome().add(row.amount()));
            case UNIQUE -> setUnique(getUnique().add(row.amount()));
        }
        setDiff(getDiff().add(row.amount()));
    }

}
