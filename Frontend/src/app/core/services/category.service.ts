import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OAuthService } from 'angular-oauth2-oidc';
import { HttpHeaders } from '@angular/common/http';
import { Category } from '../model/category';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  constructor(private http: HttpClient, private oauth: OAuthService) {}

  url: string = 'http://localhost:8080/category';

  headers = new HttpHeaders({
    Authorization: `Bearer ${this.oauth.getAccessToken()}`,
  });

  get(): Observable<any> {
    return this.http.get<Category>(this.url + '/get', {
      headers: this.headers,
    });
  }

  save(categories: Category[]): Observable<any> {
    return this.http.post<Category>(this.url + '/save', categories, {
      headers: this.headers,
    });
  }

  delete(category: Category): Observable<any> {
    return this.http.delete(this.url + '/delete/' + category.categoryID, {
      responseType: 'text',
      headers: this.headers,
    });
  }
}
