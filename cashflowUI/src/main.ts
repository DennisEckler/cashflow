import { importProvidersFrom } from '@angular/core';
import { AppComponent } from './app/app.component';
import { NgOptimizedImage } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { withInterceptorsFromDi, provideHttpClient } from '@angular/common/http';
import { BrowserModule, bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { provideAnimations } from '@angular/platform-browser/animations';

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(BrowserModule, FormsModule, NgOptimizedImage),
    provideHttpClient(withInterceptorsFromDi()),
    appConfig.providers,
    provideAnimations(),
  ],
}).catch((err) => console.error(err));
