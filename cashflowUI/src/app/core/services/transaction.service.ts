import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaction } from '../model/transaction';
import { environment } from '../../../environments/environment'
import { FileStructure } from '../model/FileStructure';

@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  url: string = environment.cashflowUrl + 'transaction/';

  constructor(private http: HttpClient) { }

  getList(): Observable<any> {
    return this.http.get<Transaction>(this.url + 'uncategorized');
  }

  saveList(transaktions: Transaction[]): Observable<any> {
    return this.http.patch(this.url + 'categorize', transaktions, {
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
    return this.http.post(this.url + 'upload', formData, {
      responseType: 'text',
    });
  }

  recategorize(): Observable<any> {
    return this.http.get(this.url + 'recategorize', {
      responseType: 'text',
    });
  }
}
