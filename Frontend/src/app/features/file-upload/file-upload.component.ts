import { Component, OnInit, inject } from '@angular/core';
import { FileUploadService } from '../../core/services/file-upload.service';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss'],
  standalone: true,
  imports: [],
})
export class FileUploadComponent implements OnInit {
  private fileUploadService = inject(FileUploadService);
  csvFile: File | null = null;
  message: string = '';
  loading: boolean = false;

  constructor() {}
  ngOnInit(): void {}

  onChange(event: any) {
    const fileUpload: File = event.target.files[0];
    if (fileUpload) {
      this.message = fileUpload.name;
      this.csvFile = fileUpload;
    }
  }

  onUpload() {
    this.loading = !this.loading;
    if (this.csvFile) {
      console.log('uploading this file: ' + this.csvFile.name);
      this.fileUploadService.upload(this.csvFile).subscribe((event: any) => {
        if (typeof event === 'object') {
          this.loading = false;
        }
      });
    }
  }
}
