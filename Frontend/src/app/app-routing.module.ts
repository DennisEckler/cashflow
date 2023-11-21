import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FileUploadComponent } from './components/file-upload/file-upload.component';
import { UpdateListComponent } from './components/update-list/update-list.component';
import { ShowTransaktionsComponent } from './components/show-transaktions/show-transaktions.component';

const routes: Routes = [
  { path: 'file-upload', component: FileUploadComponent },
  { path: 'update-list', component: UpdateListComponent },
  { path: 'show-transaktions', component: ShowTransaktionsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
