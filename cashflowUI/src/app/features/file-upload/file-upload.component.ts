import { Component, computed, inject, signal } from '@angular/core';
import { NavigationButtonComponent } from '@src/app/shared/components/navigation-button/navigation-button.component';
import { FileDescription, TransactionService } from '@generated/openapi';
import { HttpErrorResponse } from '@angular/common/http';
import { form, FormField } from '@angular/forms/signals';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss'],
  imports: [NavigationButtonComponent, FormField],
})
export class FileUploadComponent {
  csvFile = signal<File | undefined>(undefined);
  fileDescription = signal<FileDescription>({
    dateIdx: 1,
    amountIdx: 5,
    purposeIdx: 4,
    sourceIdx: 2,
    year: '2023',
    month: '08',
  });
  message = signal('');
  fileNameDescription = computed(() => this.csvFile()?.name ?? 'No file uploaded yet.');
  fileDescriptionForm = form(this.fileDescription);

  private transactionService = inject(TransactionService);

  onChange(event: Event) {
    const htmlInputEvent = event.target as HTMLInputElement;
    if (htmlInputEvent.files && htmlInputEvent.files[0]) {
      const fileUpload: File = htmlInputEvent.files[0];
      this.csvFile.set(fileUpload);
    }
  }

  onUpload() {
    const file = this.csvFile();
    if (file) {
      console.log('uploading this file: ' + file.name);
      this.transactionService.createTransactions(file, this.fileDescription()).subscribe({
        next: () => {
          this.message.set('Upload successful');
        },
        error: (err: HttpErrorResponse) => {
          this.message.set(err.error?.message ?? err.message);
          console.log(`error-msg: ${err.error?.message ?? err.message}`);
        },
      });
    }
  }
}
