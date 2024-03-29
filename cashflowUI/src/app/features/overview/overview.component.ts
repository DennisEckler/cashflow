import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OverviewService } from 'src/app/core/services/overview.service';
import { OverviewSummary } from 'src/app/core/model/overview';

@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.scss',
})
export class OverviewComponent implements OnInit {
  overviews: OverviewSummary[] = [];

  constructor(private overviewService: OverviewService) {}

  ngOnInit() {
    this.overviewService.getOverview().subscribe({
      next: (v) => (this.overviews = v),
    });
  }
}
