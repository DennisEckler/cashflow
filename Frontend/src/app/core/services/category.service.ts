import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TransaktionDTO } from '../model/transaktion-dto';
import { OAuthService } from 'angular-oauth2-oidc';
import { HttpHeaders } from '@angular/common/http';
import { Category } from '../model/category';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  url: string = 'http://localhost:8080/category';

  constructor(
    private http: HttpClient,
    private oauth: OAuthService,
  ) {}

  get(): Observable<any> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.oauth.getAccessToken()}`,
    });
    return this.http.get<Category>(this.url + '/get', { headers });
  }

  saveList(transaktions: TransaktionDTO[]): Observable<any> {
    return this.http.patch(this.url + 'categorize', transaktions, {
      responseType: 'text',
    });
  }
}
