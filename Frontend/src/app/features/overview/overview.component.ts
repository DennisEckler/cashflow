import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OverviewService } from './overview.service';
import { OverviewRow } from 'src/app/core/model/overviewRow';
import { OverviewRowComponent } from 'src/app/shared/overview-row/overview-row.component';
import { OAuthService } from 'angular-oauth2-oidc';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [CommonModule, OverviewRowComponent],
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.scss',
})
export class OverviewComponent implements OnInit {
  overviewSummary: OverviewRow[] = [];

  constructor(
    private overviewService: OverviewService,
    private httpClient: HttpClient,
    private oauthService: OAuthService
  ) {}
  ngOnInit() {
    // this.overviewService.getOverview().subscribe({
    //   next: (v) => {
    //     this.overviewSummary = v;
    //   },
    //   error: (error: HttpErrorResponse) => console.log(error.message),
    // });
  }

  logout() {
    this.oauthService.logOut();
  }

  getOverview() {
    this.httpClient
      .get<OverviewRow[]>('http://localhost:8080/overview', {
        headers: {
          Authorization: `Bearer ${this.oauthService.getAccessToken()}`,
        },
      })
      .subscribe({
        next: (v) => (this.overviewSummary = v),
        error: (error) => console.log(error),
      });
  }
}
