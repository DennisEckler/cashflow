package dev.eckler.cashflow.model.overview;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
public class OverviewService {

  public List<OverviewRow> createOverviewRows(List<OverviewEntry> overviewEntries) {
    List<OverviewRow> overviewSummary = new ArrayList<>();

    overviewEntries.stream()
        .collect(Collectors.groupingBy(OverviewEntry::getYear, Collectors.groupingBy(OverviewEntry::getMonth)))
        .forEach((year, yearEntries) -> {

          yearEntries.forEach((month, months) -> {

            OverviewRow overviewRow = new OverviewRow();
            overviewRow.setYear(months.get(0).getYear());
            overviewRow.setMonth(months.get(0).getMonth());
            months.forEach(entry -> {
              overviewRow.mapCategoryAmount(entry.getCategory(), entry.getAmount());
            });
            overviewSummary.add(overviewRow);

          });

        });

    return overviewSummary;
  }
}
