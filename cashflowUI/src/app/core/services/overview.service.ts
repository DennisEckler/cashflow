import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class OverviewService {
  constructor(private http: HttpClient, private oauth: OAuthService) {}

  getOverview(): Observable<any> {
    return this.http.get('/api/overview/');
  }
}
