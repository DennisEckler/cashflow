import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaktion } from '../model/transaktion';
import { TransaktionDTO } from '../model/transaktion-dto';

@Injectable({
  providedIn: 'root',
})
export class CategorizeService {
  url: string = 'http://localhost:8080/';

  constructor(private http: HttpClient) {}

  getList(): Observable<any> {
    return this.http.get<Transaktion>(this.url + 'update-list');
  }

  saveList(transaktions: TransaktionDTO[]): Observable<any> {
    return this.http.patch(this.url + 'categorize', transaktions, {
      responseType: 'text',
    });
  }
}
