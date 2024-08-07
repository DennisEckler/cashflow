import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../model/category';
import { environment } from '../../../environments/environment'

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  constructor(private http: HttpClient) { }

  url: string = environment.cashflowUrl + 'category/';

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
    return this.http.delete(this.url + category.id, {
      responseType: 'text',
    });
  }
}
