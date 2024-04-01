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

  url: string = '/api/category/';

  get(): Observable<any> {
    return this.http.get<Category>(this.url);
  }

  add(category: Category): Observable<any> {
    return this.http.post<Category>(this.url, category);
  }

  change(category: Category): Observable<any> {
    return this.http.patch(this.url, category);
  }

  delete(category: Category): Observable<any> {
    return this.http.delete(this.url + category.categoryID, {
      responseType: 'text',
    });
  }
}
