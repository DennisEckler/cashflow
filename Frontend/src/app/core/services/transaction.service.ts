import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaktion } from '../model/transaktion';
import { TransaktionDTO } from '../model/transaktion-dto';
import { OAuthService } from 'angular-oauth2-oidc';

@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  url: string = 'http://localhost:8080/transaction/';

  constructor(
    private http: HttpClient,
    private oauth: OAuthService,
  ) {}

  getList(): Observable<any> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.oauth.getAccessToken()}`,
    });
    return this.http.get<Transaktion>(this.url + 'uncategorized', { headers });
  }

  saveList(transaktions: TransaktionDTO[]): Observable<any> {
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
    return this.http.post(this.url + 'upload', formData, { headers });
  }

  uploadInit(file: File): Observable<any> {
    const formData = new FormData();
    if (file) {
      formData.append('file', file, file.name);
    }
    return this.http.post(this.url + 'file-upload-init', formData, {
      headers: {
        Authorization: `Bearer ${this.oauth.getAccessToken()}`,
      },
    });
  }
}
