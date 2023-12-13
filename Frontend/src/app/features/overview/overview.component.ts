import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OverviewService } from './overview.service';
import { HttpErrorResponse } from '@angular/common/http';
import { OverviewRow } from 'src/app/core/model/overviewRow';
import { OverviewRowComponent } from 'src/app/shared/overview-row/overview-row.component';

@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [CommonModule, OverviewRowComponent],
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.scss',
})
export class OverviewComponent implements OnInit {
  overviewSummary: OverviewRow[] = [];

  constructor(private overviewService: OverviewService) {}
  ngOnInit() {
    this.overviewService.getOverview().subscribe({
      next: (v) => {
        this.overviewSummary = v;
      },
      error: (error: HttpErrorResponse) => console.log(error.message),
    });
  }
}
