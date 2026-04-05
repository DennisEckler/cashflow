import { Component, OnInit } from '@angular/core';
import { OverviewService, OverviewSummaryResponse } from 'generated-sources/openapi';




@Component({
  selector: 'app-overview',
  imports: [],
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.scss',
})
export class OverviewComponent implements OnInit {
  overviews: OverviewSummaryResponse[] = [];

  constructor(private overviewService: OverviewService) {}

  ngOnInit() {
    this.overviewService.getOverview().subscribe({
      next: (v) => (this.overviews = v),
    });
  }
}
