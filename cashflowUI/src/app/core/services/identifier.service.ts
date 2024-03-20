import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { Observable } from 'rxjs';
import { Identifier } from '../model/identifier';
import { IdentifierDTO } from '../model/identifierDto';

@Injectable({
  providedIn: 'root',
})
export class IdentifierService {
  constructor(private http: HttpClient, private oauth: OAuthService) {}

  url: string = 'http://localhost:8080/api/identifier';

  header = new HttpHeaders({
    Authorization: `Bearer ${this.oauth.getAccessToken()}`,
  });

  delete(identifier: Identifier): Observable<any> {
    return this.http.delete(this.url + '/' + identifier.identifierID, {
      responseType: 'text',
      headers: this.header,
    });
  }

  save(identifierDTO: IdentifierDTO): Observable<any> {
    return this.http.post(this.url + '/', identifierDTO, {
      headers: this.header,
    });
  }
}
