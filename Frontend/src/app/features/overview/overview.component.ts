import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OverviewService } from './overview.service';
import { HttpErrorResponse, HttpHeaderResponse } from '@angular/common/http';

@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.scss',
})
export class OverviewComponent implements OnInit {
  constructor(private overviewService: OverviewService) {}
  ngOnInit() {
    this.overviewService.getOverview().subscribe({
      next: (v: HttpHeaderResponse) => {
        console.log(v.statusText);
      },
      error: (error: HttpErrorResponse) => console.log(error.message),
    });
  }
}
