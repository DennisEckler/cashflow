import {Component, inject} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './core/layout/header/header.component';
import {authCodeFlowConfig} from "./core/auth/auth.config";
import {OAuthService} from "angular-oauth2-oidc";

@Component({
  selector: 'app-root',
  template: `
    <app-header></app-header>
    <router-outlet></router-outlet>
  `,
  styleUrls: ['./app.component.scss'],
  imports: [HeaderComponent, RouterOutlet],
})
export class AppComponent {
  private authService =  inject(OAuthService);
  title = 'Cashflow';
  constructor() {
    this.authService.configure(authCodeFlowConfig);
    this.authService.loadDiscoveryDocumentAndTryLogin();
    this.authService.setupAutomaticSilentRefresh();
  }
}
