import { Component, OnInit, inject } from '@angular/core';
import { NavigationButtonComponent } from 'src/app/shared/navigation-button/navigation-button.component';
import { TransactionService } from 'src/app/core/services/transaction.service';
import { FormsModule } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { fileStructure } from 'src/app/core/model/fileStructure';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss'],
  standalone: true,
  imports: [NavigationButtonComponent, FormsModule],
})
export class FileUploadComponent implements OnInit {
  private transactionService = inject(TransactionService);
  csvFile: File | null = null;
  fileName: string = '';
  message: any;
  fileStructure: fileStructure = {
    dateIdx: 1,
    amountIdx: 5,
    purposeIdx: 4,
    sourceIdx: 2,
    yearIdx: '2023',
    monthIdx: '08',
  };

  constructor() {}
  ngOnInit(): void {}

  onChange(event: any) {
    console.log('test');
    const fileUpload: File = event.target.files[0];
    if (fileUpload) {
      this.fileName = fileUpload.name;
      this.csvFile = fileUpload;
    }
  }

  updateJson(event: any) {
    this.fileStructure = event.target;
  }

  onUpload() {
    if (this.csvFile) {
      console.log('uploading this file: ' + this.csvFile.name);
      this.transactionService
        .upload(this.csvFile, this.fileStructure)
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
