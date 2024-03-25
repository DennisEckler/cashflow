import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class OverviewService {
  constructor(
    private http: HttpClient,
    private oauth: OAuthService,
  ) {}

  header = new HttpHeaders({
    Authorization: `Bearer ${this.oauth.getAccessToken()}`,
  });

  getOverview(): Observable<any> {
    return this.http.get('http://localhost:8080/api/overview/', {
      headers: this.header,
    });
  }
}
