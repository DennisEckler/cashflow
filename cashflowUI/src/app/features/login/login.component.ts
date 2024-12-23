import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { authCodeFlowConfig } from 'src/app/core/auth/auth.config';
import { NavigationButtonComponent } from 'src/app/shared/navigation-button/navigation-button.component';

@Component({
  selector: 'app-login',
  imports: [CommonModule, NavigationButtonComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent implements OnInit {
  constructor(private oauth: OAuthService) {}

  ngOnInit(): void {
    this.oauth.configure(authCodeFlowConfig);
    this.oauth.loadDiscoveryDocumentAndTryLogin();
  }

  onLogin() {
    this.oauth.initCodeFlow();
  }
  onLogout() {
    this.oauth.revokeTokenAndLogout();
  }
}
