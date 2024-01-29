import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationButtonComponent } from 'src/app/shared/navigation-button/navigation-button.component';
import { OAuthService } from 'angular-oauth2-oidc';
import {
  authCodeFlowConfig,
  // initializeOAuth,
} from 'src/app/core/auth/auth.conf';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, NavigationButtonComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  constructor(private oauthService: OAuthService) {}

  onLogin() {
    // initializeOAuth(this.oauthService);
    this.oauthService.configure(authCodeFlowConfig);
    // this.oauthService.initCodeFlow();
    this.oauthService.loadDiscoveryDocumentAndLogin();
  }

  onLogout() {
    this.oauthService.logOut();
  }
}
