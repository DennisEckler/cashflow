import { inject } from '@angular/core';
import { HttpRequest, HttpHandlerFn } from '@angular/common/http';
import { OAuthService } from 'angular-oauth2-oidc';

export function authInterceptor(
  req: HttpRequest<unknown>,
  next: HttpHandlerFn,
) {
  if (req.url.includes('/api/')) {
    const authToken = inject(OAuthService).getAccessToken();
    if (authToken) {
      const bearerToken = 'Bearer ' + authToken;
      const newReq = req.clone({
        headers: req.headers.append('Authorization', bearerToken),
      });
      return next(newReq);
    }
  }
  return next(req);
}
