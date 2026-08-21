import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { environment } from '@src/environments/environment';
import { Configuration } from '@generated/openapi';

export const apiConfig = new Configuration({
  basePath: environment.apiBaseUrl,
});

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    { provide: Configuration, useValue: apiConfig },
  ],
};
