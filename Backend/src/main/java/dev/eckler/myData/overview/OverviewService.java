package dev.eckler.myData.overview;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class OverviewService {
  private String year = "";
  private String month = "";
  private int index = 0;
  private OverviewRow overviewRow = new OverviewRow();

  public List<OverviewRow> createOverviewRows(List<OverviewEntry> overviewEntries) {
    List<OverviewRow> overviewSummary = new ArrayList<>();

    overviewEntries.forEach(entry -> {

      if (index == 0) {
        this.year = entry.getYear();
        this.month = entry.getMonth();
        this.overviewRow.setYear(entry.getYear());
        this.overviewRow.setMonth(entry.getMonth());
        index++;
      }

      if (entry.getYear().equals(year) && entry.getMonth().equals(month)) {
        overviewRow.mapCategoryAmount(entry.getCategory(), entry.getAmount());
      } else {

        System.out.println(overviewRow.toString());
        overviewSummary.add(overviewRow);
        this.year = entry.getYear();
        this.month = entry.getMonth();
        this.overviewRow = new OverviewRow();
        this.overviewRow.setYear(entry.getYear());
        this.overviewRow.setMonth(entry.getMonth());

      }
    });

    this.year = "";
    this.month = "";
    this.index = 0;
    return overviewSummary;
  }

}
