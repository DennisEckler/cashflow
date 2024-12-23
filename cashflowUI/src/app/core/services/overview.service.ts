import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { OAuthService } from 'angular-oauth2-oidc';

@Injectable({
  providedIn: 'root',
})
export class OverviewService {
  constructor(
    private http: HttpClient,
    private oauth: OAuthService,
  ) {}

  token = this.oauth.getAccessToken();

  httpHeader = new HttpHeaders({
    Authorization: `Bearer ${this.token}`,
  });

  getOverview(): Observable<any> {
    return this.http.get(environment.cashflowUrl + '/overview/', {
      headers: this.httpHeader,
    });
  }
}
