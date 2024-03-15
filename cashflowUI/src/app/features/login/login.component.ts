import { Component, OnInit } from '@angular/core';
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
export class LoginComponent implements OnInit {
  constructor(private oauthService: OAuthService) {}

  ngOnInit(): void {
    this.oauthService.configure(authCodeFlowConfig);
    this.oauthService.loadDiscoveryDocumentAndTryLogin();
  }

  onLogin() {
    this.oauthService.initCodeFlow();
  }

  onLogout() {
    this.oauthService.logOut();
  }
}
