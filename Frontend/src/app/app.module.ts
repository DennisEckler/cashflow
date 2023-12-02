import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgOptimizedImage } from '@angular/common';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FileUploadComponent } from './components/file-upload/file-upload.component';
import { HttpClientModule } from '@angular/common/http';
import { UpdateListComponent } from './components/update-list/update-list.component';
import { HeaderComponent } from './components/header/header.component';
import { ShowTransaktionsComponent } from './components/show-transaktions/show-transaktions.component';

@NgModule({
  declarations: [
    AppComponent,
    FileUploadComponent,
    UpdateListComponent,
    HeaderComponent,
    ShowTransaktionsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgOptimizedImage,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
