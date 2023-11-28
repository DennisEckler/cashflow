import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaktion } from 'src/app/shared/model/transaktion';
import { CategoryIdentifier } from './update-list.component';

@Injectable({
  providedIn: 'root',
})
export class UpdateListService {
  url: string = 'http://localhost:8080/';

  constructor(private http: HttpClient) {}

  getList(): Observable<any> {
    return this.http.get<Transaktion>(this.url + 'update-list');
  }

  saveList(transaktions: CategoryIdentifier[]): Observable<any> {
    return this.http.patch(this.url + 'categorize', transaktions, {
      responseType: 'text',
    });
  }
}
