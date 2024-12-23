import { NgOptimizedImage } from '@angular/common';
import {
  provideHttpClient,
  withInterceptorsFromDi,
} from '@angular/common/http';
import { importProvidersFrom } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule, bootstrapApplication } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideOAuthClient } from 'angular-oauth2-oidc';
import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config';

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(BrowserModule, FormsModule, NgOptimizedImage),
    provideHttpClient(withInterceptorsFromDi()),
    provideOAuthClient(),
    appConfig.providers,
    provideAnimations(),
  ],
}).catch((err) => console.error(err));
