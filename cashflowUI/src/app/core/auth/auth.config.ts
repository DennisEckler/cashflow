import { AuthConfig } from 'angular-oauth2-oidc';

export const authCodeFlowConfig: AuthConfig = {
  issuer: 'http://localhost:8099/realms/cashflow_realm',
  redirectUri: window.location.origin + '/login',
  clientId: 'cashflow',
  responseType: 'code',
  scope: 'openid profile email offline_access',
  showDebugInformation: true,
  requireHttps: false,
};
