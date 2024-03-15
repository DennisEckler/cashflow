import { Component, OnInit, inject } from '@angular/core';
import { NavigationButtonComponent } from 'src/app/shared/navigation-button/navigation-button.component';
import { TransactionService } from 'src/app/core/services/transaction.service';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss'],
  standalone: true,
  imports: [NavigationButtonComponent],
})
export class FileUploadComponent implements OnInit {
  private transactionService = inject(TransactionService);
  csvFile: File | null = null;
  message: string = '';
  text: string = '';

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
      this.text =
        "{date: 1, amount: 5,purpose: 4, source: 2, blankRows: 13, year: '2023', month: '08'}";
      console.log('uploading this file: ' + this.csvFile.name);
      this.transactionService
        .upload(this.csvFile, this.text)
        .subscribe((event: any) => {
          if (typeof event === 'object') {
            this.loading = false;
          }
        });
    }
  }
}
