import {Routes} from '@angular/router';
import {HomeComponent} from './features/home/home.component';
import {LoginComponent} from './features/login/login.component';

export const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', title: 'Home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {
    path: 'file-upload',
    title: 'FileUpload',
    loadComponent: () => import('./features/file-upload/file-upload.component').then(m => m.FileUploadComponent)
  },
  {
    path: 'categorize',
    title: 'Categorize',
    loadComponent: () => import('./features/categorize/categorize.component').then(m => m.CategorizeComponent)
  },
  {
    path: 'overview',
    title: 'Overview',
    loadComponent: () => import('./features/overview/overview.component').then(m => m.OverviewComponent)
  },
  {
    path: 'settings',
    title: 'Settings',
    loadComponent: () => import('./features/settings/settings.component').then(m => m.SettingsComponent),
  },
];
