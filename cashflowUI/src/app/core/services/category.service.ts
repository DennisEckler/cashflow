import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../model/category';
import { environment } from '../../../environments/environment';
import { OAuthService } from 'angular-oauth2-oidc';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  constructor(
    private http: HttpClient,
    private oauth: OAuthService,
  ) {}

  url: string = environment.cashflowUrl + '/category/';
  token: string = this.oauth.getAccessToken();

  httpHeader = new HttpHeaders({
    Authorization: `Bearer ${this.token}`,
  });

  get(): Observable<any> {
    return this.http.get<Category>(this.url, { headers: this.httpHeader });
  }

  add(category: Category): Observable<any> {
    return this.http.post<Category>(this.url, category, {
      headers: this.httpHeader,
    });
  }

  change(category: Category): Observable<any> {
    return this.http.patch(this.url, category, { headers: this.httpHeader });
  }

  delete(category: Category): Observable<any> {
    return this.http.delete(this.url + category.id, {
      responseType: 'text',
      headers: this.httpHeader,
    });
  }
}
