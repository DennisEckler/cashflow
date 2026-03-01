import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class OverviewService {
  constructor(private http: HttpClient) {}
  getOverview(): Observable<any> {
    return this.http.get('http://cashflow-api:8080/v1/api/overview');
  }
}
