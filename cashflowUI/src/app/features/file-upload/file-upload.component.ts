import { Component, OnInit, inject } from '@angular/core';
import { NavigationButtonComponent } from 'src/app/shared/navigation-button/navigation-button.component';
import { FormsModule } from '@angular/forms';
import { FileDescription, TransactionService } from 'generated-sources/openapi';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss'],
  imports: [NavigationButtonComponent, FormsModule],
})
export class FileUploadComponent implements OnInit {
  private transactionService = inject(TransactionService);
  csvFile: File | null = null;
  fileName: string = '';
  message: any;

  fileDescription: FileDescription = {
    dateIdx: 1,
    amountIdx: 5,
    purposeIdx: 4,
    sourceIdx: 2,
    year: '2023',
    month: '08',
  };

  constructor() {}
  ngOnInit(): void {}

  onChange(event: any) {
    const fileUpload: File = event.target.files[0];
    if (fileUpload) {
      this.fileName = fileUpload.name;
      this.csvFile = fileUpload;
    }
  }

  updateJson(event: any) {
    this.fileDescription = event.target;
  }

  onUpload() {
    if (this.csvFile) {
      console.log('uploading this file: ' + this.csvFile.name);
      this.transactionService
        .createTransactions(this.csvFile, this.fileDescription)
        .subscribe({
          next: (response) => {
            this.message = response;
          },
          error: (err) => {
            this.message = err.message;
            console.log('error' + err);
          },
        });
    }
  }
}
