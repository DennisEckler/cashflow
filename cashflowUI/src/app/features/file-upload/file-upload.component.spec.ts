import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { of, throwError } from 'rxjs';
import { beforeEach, describe, expect, it, vi } from 'vitest';
import { FileUploadComponent } from './file-upload.component';
import { TransactionService } from '@generated/openapi';
import { FormField } from '@angular/forms/signals';

function createMockFile(name: string = 'test.csv'): File {
  return new File(['dummy content'], name, { type: 'text/csv' });
}

describe('FileUploadComponent', () => {
  let component: FileUploadComponent;
  let fixture: ComponentFixture<FileUploadComponent>;
  let transactionServiceMock: any;

  function selectFile(file: File) {
    const input = fixture.debugElement.query(By.css('input[type="file"]')).nativeElement;

    Object.defineProperty(input, 'files', {
      value: [file],
      configurable: true,
    });

    input.dispatchEvent(new Event('change'));
    fixture.detectChanges();
  }

  beforeEach(async () => {
    transactionServiceMock = {
      createTransactions: vi.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [FileUploadComponent, FormField],
      providers: [{ provide: TransactionService, useValue: transactionServiceMock }],
    }).compileComponents();

    fixture = TestBed.createComponent(FileUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  describe('Initial state', () => {
    it('should have no file selected initially', () => {
      expect(component.csvFile()).toBeUndefined();
      expect(component.fileNameDescription()).toBe('No file uploaded yet.');
    });

    it('should NOT render the file structure block initially', () => {
      const fileStructureDiv = fixture.debugElement.query(By.css('.file-structure'));
      expect(fileStructureDiv).toBeNull();
    });

    it('should display the default file name description', () => {
      const displayElement = fixture.debugElement.query(By.css('.file-upload')).nativeElement;
      expect(displayElement.textContent).toContain('No file uploaded yet.');
    });
  });

  describe('File selection', () => {
    it('should update the csvFile signal and computed description when a file is selected', () => {
      const mockFile = createMockFile('my-data.csv');
      selectFile(mockFile);

      expect(component.csvFile()).toBe(mockFile);
      expect(component.fileNameDescription()).toBe('my-data.csv');
    });

    it('should render the file structure block after a file is selected', () => {
      selectFile(createMockFile('test.csv'));

      const fileStructureDiv = fixture.debugElement.query(By.css('.file-structure'));
      expect(fileStructureDiv).toBeTruthy();
    });

    it('should display the file name in the UI', () => {
      const mockFile = createMockFile('awesome.csv');
      selectFile(mockFile);

      const displayElement = fixture.debugElement.query(By.css('.file-upload')).nativeElement;
      expect(displayElement.textContent).toContain('awesome.csv');
    });
  });

  describe('Upload button and message', () => {
    it('should call transactionService.createTransactions and set message on success', () => {
      const mockFile = createMockFile('upload.csv');

      transactionServiceMock.createTransactions.mockReturnValue(of(void 0));

      selectFile(mockFile);

      const sendButton = fixture.debugElement.query(By.css('navigation-button.send-button'));
      expect(sendButton).toBeTruthy();
      sendButton.nativeElement.click();
      fixture.detectChanges();

      expect(transactionServiceMock.createTransactions).toHaveBeenCalledWith(
        mockFile,
        component.fileDescription(),
      );
      expect(component.message()).toBe('Upload successful');
      const allText = fixture.debugElement.nativeElement.textContent;
      expect(allText).toContain('Upload successful');
    });

    it('should set error message on upload failure', () => {
      const mockFile = createMockFile('error.csv');
      const error = {
        status: 404,
        error: {
          statusCode: 404,
          message: 'Transaction not found',
        },
      };

      transactionServiceMock.createTransactions.mockReturnValue(throwError(() => error));

      selectFile(mockFile);

      const sendButton = fixture.debugElement.query(By.css('navigation-button.send-button'));
      sendButton.nativeElement.click();
      fixture.detectChanges();

      expect(component.message()).toBe('Transaction not found');
      const allText = fixture.debugElement.nativeElement.textContent;
      expect(allText).toContain('Transaction not found');
    });

    it('should NOT call service if no file is selected', () => {
      expect(component.csvFile()).toBeUndefined();

      const sendButton = fixture.debugElement.query(By.css('navigation-button.send-button'));
      expect(sendButton).toBeNull();

      component.onUpload();
      expect(transactionServiceMock.createTransactions).not.toHaveBeenCalled();
    });
  });

  describe('FileDescription binding', () => {
    it('should update fileDescription via signal forms bindings', () => {
      selectFile(createMockFile('test.csv'));

      const dateIdxInput = fixture.debugElement.query(By.css('input#dateIdx'));
      expect(dateIdxInput).toBeTruthy();

      dateIdxInput.nativeElement.value = '3';
      dateIdxInput.nativeElement.dispatchEvent(new Event('input'));
      fixture.detectChanges();

      expect(component.fileDescription().dateIdx).toBe(3);
    });
  });
});
