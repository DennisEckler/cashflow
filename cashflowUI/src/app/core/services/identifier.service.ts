import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Identifier } from '../model/identifier';
import { IdentifierDTO } from '../model/identifierDto';
import { environment } from '../../../environments/environment'

@Injectable({
  providedIn: 'root',
})
export class IdentifierService {
  constructor(private http: HttpClient) { }

  url: string = environment.cashflowUrl + 'identifier/';

  delete(identifier: Identifier): Observable<any> {
    return this.http.delete(this.url + identifier.id, {
      responseType: 'text',
    });
  }

  save(identifierDTO: IdentifierDTO): Observable<any> {
    return this.http.post(this.url, identifierDTO);
  }
}
