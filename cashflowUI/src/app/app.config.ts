import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideHttpClient } from '@angular/common/http';
import { Configuration } from 'generated-sources/openapi';
import { environment } from 'src/environments/environment';

export const apiConfig = new Configuration({
  basePath: environment.apiBaseUrl,
});

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    { provide: Configuration, useValue: apiConfig },
  ],
};
