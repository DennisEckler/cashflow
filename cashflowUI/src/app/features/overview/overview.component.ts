import { Component, OnInit } from '@angular/core';
import { ExpensesService, MonthlySummaryTest, OverviewService, OverviewSummaryResponse } from 'generated-sources/openapi';




@Component({
  selector: 'app-overview',
  imports: [],
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.scss',
})
export class OverviewComponent implements OnInit {
  overviews: OverviewSummaryResponse[] = [];
  monthlySummary: MonthlySummaryTest[] = [];
  categoryKeys: string[] = [];


  constructor(private overviewService: OverviewService, private expenseService: ExpensesService) { }

  ngOnInit() {
    this.overviewService.getOverview().subscribe({
      next: (v) => (this.overviews = v),
      error: (e) => console.error('OVERVIEW ERROR', e),
    });
    this.expenseService.getExpenses().subscribe({
      next: (x) => {
        console.log('EXPENSES OK', x)
        this.monthlySummary = x;
        const keySet = new Set<string>();


        if (this.monthlySummary.length > 0) {
          Object.keys(this.monthlySummary[0].categoryTotals)
            .forEach(key => keySet.add(key));
        }


        this.categoryKeys = Array.from(keySet);
      },
      error: (e) => console.error('EXPENSES ERROR', e),
    });
  }
}
