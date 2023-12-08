import { Routes } from '@angular/router';
import { FileUploadComponent } from './features/file-upload/file-upload.component';
import { CategorizeComponent } from './features/categorize/categorize.component';
import { ShowTransaktionsComponent } from './features/show-transaktions/show-transaktions.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';

export const routes: Routes = [
  { path: 'file-upload', title: 'FileUpload', component: FileUploadComponent },
  { path: 'categorize', component: CategorizeComponent },
  { path: 'show-transaktions', component: ShowTransaktionsComponent },
  { path: 'dashboard', component: DashboardComponent },
];
