
import { Component } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { NavigationButtonComponent } from '@src/app/shared/navigation-button/navigation-button.component';

@Component({
  selector: 'app-login',
  imports: [NavigationButtonComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  constructor(private oauth: OAuthService) {}

  onLogin() {
    this.oauth.initCodeFlow();
  }
  onLogout() {
    this.oauth.revokeTokenAndLogout();
  }
}
