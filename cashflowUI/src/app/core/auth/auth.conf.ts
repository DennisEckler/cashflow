import { AuthConfig, OAuthService } from 'angular-oauth2-oidc';

export const authCodeFlowConfig: AuthConfig = {
  issuer: '/realms/cashflow',
  tokenEndpoint: '/realms/cashflow/protocol/openid-connect/token',
  redirectUri: window.location.origin + '/login',
  clientId: 'cashflowClient',
  responseType: 'code',
  scope: 'profile',
  showDebugInformation: true,
  requireHttps: false,
};

export function initializeOAuth(oauthService: OAuthService): Promise<void> {
  return new Promise((resolve) => {
    oauthService.configure(authCodeFlowConfig);
    oauthService.setupAutomaticSilentRefresh();
    oauthService.loadDiscoveryDocumentAndLogin().then(() => resolve());
  });
}
