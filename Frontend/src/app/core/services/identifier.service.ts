import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';

@Injectable({
  providedIn: 'root',
})
export class IdentifierService {
  url: string = 'http://localhost:8080/identifier';

  constructor(private http: HttpClient, private oauth: OAuthService) {}
}
