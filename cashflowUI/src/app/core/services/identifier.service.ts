import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Identifier } from '../model/identifier';
import { IdentifierDTO } from '../model/identifierDto';

@Injectable({
  providedIn: 'root',
})
export class IdentifierService {
  constructor(private http: HttpClient) {}

  url: string = '/api/identifier/';

  delete(identifier: Identifier): Observable<any> {
    return this.http.delete(this.url + identifier.identifierID, {
      responseType: 'text',
    });
  }

  save(identifierDTO: IdentifierDTO): Observable<any> {
    return this.http.post(this.url, identifierDTO);
  }
}
