import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaction } from '../model/transaction';
import { environment } from '../../../environments/environment';
import { FileStructure } from '../model/fileStructure';
import { OAuthService } from 'angular-oauth2-oidc';

@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  constructor(
    private http: HttpClient,
    private oauth: OAuthService,
  ) {}

  url: string = environment.cashflowUrl + '/transaction';
  token: string = this.oauth.getAccessToken();

  httpHeader = new HttpHeaders({
    Authorization: `Bearer ${this.token}`,
  });

  getList(): Observable<any> {
    return this.http.get<Transaction>(this.url + '/uncategorized', {
      headers: this.httpHeader,
    });
  }

  saveList(transaktions: Transaction[]): Observable<any> {
    return this.http.patch(this.url + '/categorize', transaktions, {
      headers: this.httpHeader,
      responseType: 'text',
    });
  }

  upload(file: File, fileStructure: FileStructure): Observable<any> {
    const formData = new FormData();
    if (file) {
      formData.append('file', file, file.name);
    }
    if (fileStructure) {
      formData.append('fileStructure', JSON.stringify(fileStructure));
    }
    return this.http.post(this.url + '/upload', formData, {
      headers: this.httpHeader,
      responseType: 'text',
    });
  }

  recategorize(): Observable<any> {
    return this.http.get(this.url + '/recategorize', {
      headers: this.httpHeader,
      responseType: 'text',
    });
  }
}
