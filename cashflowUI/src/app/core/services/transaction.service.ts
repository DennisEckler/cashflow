import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaction } from '../model/transaction';
import { OAuthService } from 'angular-oauth2-oidc';

@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  url: string = '/api/transaction/';

  constructor(private http: HttpClient, private oauth: OAuthService) {}

  getList(): Observable<any> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.oauth.getAccessToken()}`,
    });
    return this.http.get<Transaction>(this.url + 'uncategorized', { headers });
  }

  saveList(transaktions: Transaction[]): Observable<any> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.oauth.getAccessToken()}`,
    });
    return this.http.patch(this.url + 'categorize', transaktions, {
      responseType: 'text',
      headers,
    });
  }

  upload(file: File, text: string): Observable<any> {
    const formData = new FormData();
    if (file) {
      formData.append('file', file, file.name);
    }
    if (text) {
      formData.append('columnIndex', text);
    }
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.oauth.getAccessToken()}`,
    });
    return this.http.post(this.url + 'upload', formData, {
      responseType: 'text',
      headers,
    });
  }
}
