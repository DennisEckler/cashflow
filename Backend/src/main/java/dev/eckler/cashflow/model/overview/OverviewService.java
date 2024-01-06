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

    // overviewEntries.forEach(entry -> {
    //
    // if (index == 0) {
    // this.year = entry.getYear();
    // this.month = entry.getMonth();
    // this.overviewRow.setYear(entry.getYear());
    // this.overviewRow.setMonth(entry.getMonth());
    // index++;
    // }
    //
    // if (entry.getYear().equals(year) && entry.getMonth().equals(month)) {
    // overviewRow.mapCategoryAmount(entry.getCategory(), entry.getAmount());
    // } else {
    //
    // overviewSummary.add(overviewRow);
    // this.year = entry.getYear();
    // this.month = entry.getMonth();
    // this.overviewRow = new OverviewRow();
    // this.overviewRow.setYear(entry.getYear());
    // this.overviewRow.setMonth(entry.getMonth());
    //
    // }
    // });
    //
    // this.year = "";
    // this.month = "";
    // this.index = 0;
    return overviewSummary;
  }
}
