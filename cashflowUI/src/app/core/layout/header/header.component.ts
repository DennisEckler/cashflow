import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgOptimizedImage } from '@angular/common';
import { CashflowButtonComponent } from '@src/app/shared/components/cashflow-button/cashflow-button.component';

@Component({
  selector: 'app-header',
  template: `<img
      class="logo"
      [ngSrc]="logo"
      height="200"
      width="200"
      alt="Logo an open book"
      priority
    />
    <cashflow-button routerLink="/home" label="Home" />
    <cashflow-button routerLink="/overview" label="Overview" />
    <cashflow-button routerLink="/file-upload" label="FileUpload" />
    <cashflow-button routerLink="/categorize" label="Categorize" />
    <cashflow-button routerLink="/settings" label="Settings" />
    <cashflow-button routerLink="/login" label="Login" />`,
  styles: `
    .logo {
      position: absolute;
      left: 10px;
    }

    :host {
      display: grid;
      height: 170px;
      justify-content: center;
      justify-items: center;
      align-items: center;
      grid-template-columns: repeat(7, auto);
      gap: 10px;
      padding: 10px;
    }
  `,
  imports: [NgOptimizedImage, RouterLink, CashflowButtonComponent],
})
export class HeaderComponent {
  logo = '../../../assets/cashes-logo.png';
}
