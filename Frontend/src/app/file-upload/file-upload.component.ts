import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss'],
})
export class FileUploadComponent {
  clickedEvent: boolean = false;
  csvFile: File | null = null;
  message: string = '';

  onClick() {}
  onChange(event: any) {
    const fileUpload: File = event.target.files[0];
    if (fileUpload) {
      this.message = fileUpload.name;
    }
  }
}
