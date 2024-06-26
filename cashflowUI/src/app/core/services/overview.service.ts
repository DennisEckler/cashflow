import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class OverviewService {
  constructor(private http: HttpClient) { }

  getOverview(): Observable<any> {
    return this.http.get('/api/overview/');
    // return this.http.get('http://localhost:8080/api/overview/');
  }
}
