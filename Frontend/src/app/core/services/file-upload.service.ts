import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OAuthService } from 'angular-oauth2-oidc';

@Injectable({
  providedIn: 'root',
})
export class FileUploadService {
  url = 'http://localhost:8080/';

  constructor(private http: HttpClient, private oauth: OAuthService) {}

  upload(file: File): Observable<any> {
    const formData = new FormData();
    if (file) {
      formData.append('file', file, file.name);
    }
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.oauth.getAccessToken()}`,
    });
    return this.http.post(this.url + 'file-upload', formData, { headers });
  }

  uploadInit(file: File): Observable<any> {
    const formData = new FormData();
    if (file) {
      formData.append('file', file, file.name);
    }
    return this.http.post(this.url + 'file-upload-init', formData);
  }
}
