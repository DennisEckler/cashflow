import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaction } from '../model/transaction';

@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  url: string = '/api/transaction/';

  constructor(private http: HttpClient) {}

  getList(): Observable<any> {
    return this.http.get<Transaction>(this.url + 'uncategorized');
  }

  saveList(transaktions: Transaction[]): Observable<any> {
    return this.http.patch(this.url + 'categorize', transaktions, {
      responseType: 'text',
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
    return this.http.post(this.url + 'upload', formData, {
      responseType: 'text',
    });
  }
}
