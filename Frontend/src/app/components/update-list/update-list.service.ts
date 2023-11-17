import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaktion } from 'src/app/shared/model/transaktion';

@Injectable({
  providedIn: 'root',
})
export class UpdateListService {
  url: string = 'http://localhost:8080/update-list';

  constructor(private http: HttpClient) {}

  getList(): Observable<any> {
    return this.http.get<Transaktion>(this.url);
  }
}
