import { AuthConfig } from 'angular-oauth2-oidc';
import { environment } from 'src/environments/environment';

export const authCodeFlowConfig: AuthConfig = {
  issuer: environment.issuer,
  redirectUri: window.location.origin + '/login',
  clientId: 'cashflow',
  responseType: 'code',
  scope: 'openid profile email offline_access',
  showDebugInformation: true,
  requireHttps: false,
};
