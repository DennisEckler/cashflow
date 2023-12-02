import { Routes } from '@angular/router';
import { FileUploadComponent } from './components/file-upload/file-upload.component';
import { UpdateListComponent } from './components/update-list/update-list.component';
import { ShowTransaktionsComponent } from './components/show-transaktions/show-transaktions.component';

export const routes: Routes = [
  { path: 'file-upload', title: 'FileUpload', component: FileUploadComponent },
  { path: 'update-list', component: UpdateListComponent },
  { path: 'show-transaktions', component: ShowTransaktionsComponent },
];
