import { Routes } from '@angular/router';
import { FileUploadComponent } from './features/file-upload/file-upload.component';
import { CategorizeComponent } from './features/categorize/categorize.component';
import { HomeComponent } from './features/home/home.component';
import { OverviewComponent } from './features/overview/overview.component';
import { SettingsComponent } from './features/settings/settings.component';

export const routes: Routes = [
  { path: 'file-upload', title: 'FileUpload', component: FileUploadComponent },
  { path: 'categorize', component: CategorizeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'overview', component: OverviewComponent },
  { path: 'settings', component: SettingsComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
];
