import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaktion } from '../model/transaktion';
import { TransaktionDTO } from '../model/transaktion-dto';
import { OAuthService } from 'angular-oauth2-oidc';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  url: string = 'http://localhost:8080/category';

  constructor(private http: HttpClient, private oauth: OAuthService) {}

  getList(): Observable<any> {
    return this.http.get<Transaktion>(this.url + 'get-empty-category-entries');
  }

  saveList(transaktions: TransaktionDTO[]): Observable<any> {
    return this.http.patch(this.url + 'categorize', transaktions, {
      responseType: 'text',
    });
  }
}
