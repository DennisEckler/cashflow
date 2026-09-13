import { Component } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { CashflowButtonComponent } from '@src/app/shared/components/cashflow-button/cashflow-button.component';

@Component({
  selector: 'app-login',
  imports: [CashflowButtonComponent],
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
