import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UpdateListService {
  url: string = 'http://localhost:8080/update-list';

  constructor(private http: HttpClient) {}

  getList(): Observable<any> {
    return this.http.get(this.url);
  }
}
