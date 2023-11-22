import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Transaktion } from '../../shared/model/transaktion';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ShowTransaktionsService {
  url: string = 'http://localhost:8080/show-transaktions';

  constructor(private http: HttpClient) {}

  getTransaktions(): Observable<any> {
    return this.http.get<Transaktion>(this.url);
  }
}
