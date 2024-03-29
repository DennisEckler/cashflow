import { AuthConfig, OAuthService } from 'angular-oauth2-oidc';

export const authCodeFlowConfig: AuthConfig = {
  issuer: 'http://localhost:8180/realms/cashflow',
  tokenEndpoint:
    'http://localhost:8180/realms/cashflow/protocol/openid-connect/token',
  redirectUri: window.location.origin + '/login',
  clientId: 'cashflowClient',
  responseType: 'code',
  scope: 'profile',
  showDebugInformation: true,
};

export function initializeOAuth(oauthService: OAuthService): Promise<void> {
  return new Promise((resolve) => {
    oauthService.configure(authCodeFlowConfig);
    oauthService.setupAutomaticSilentRefresh();
    oauthService.loadDiscoveryDocumentAndLogin().then(() => resolve());
  });
}
