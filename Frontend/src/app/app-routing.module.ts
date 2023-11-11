import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FileUploadComponent } from './file-upload/file-upload.component';
import { UpdateListComponent } from './update-list/update-list.component';

const routes: Routes = [
  { path: 'file-upload', component: FileUploadComponent },
  { path: 'update-list', component: UpdateListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
