import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FileUploadService {
  url = 'http://localhost:8080/';

  constructor(private http: HttpClient) {}

  upload(file: File): Observable<any> {
    const formData = new FormData();
    if (file) {
      formData.append('file', file, file.name);
    }
    return this.http.post(this.url + 'file-upload', formData);
  }
  uploadInit(file: File): Observable<any> {
    const formData = new FormData();
    if (file) {
      formData.append('file', file, file.name);
    }
    return this.http.post(this.url + 'file-upload-init', formData);
  }
}
