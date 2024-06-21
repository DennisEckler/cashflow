import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Identifier } from '../model/identifier';
import { IdentifierDTO } from '../model/identifierDto';

@Injectable({
  providedIn: 'root',
})
export class IdentifierService {
  constructor(private http: HttpClient) { }

  url: string = '/api/identifier/';
  // url: string = 'http://localhost:8080/api/identifier/';

  delete(identifier: Identifier): Observable<any> {
    return this.http.delete(this.url + identifier.id, {
      responseType: 'text',
    });
  }

  save(identifierDTO: IdentifierDTO): Observable<any> {
    return this.http.post(this.url, identifierDTO);
  }
}
