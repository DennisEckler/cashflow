import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgOptimizedImage } from '@angular/common';
import { NavigationButtonComponent } from '@src/app/shared/components/navigation-button/navigation-button.component';

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
    <navigation-button routerLink="/home" label="Home" />
    <navigation-button routerLink="/overview" label="Overview" />
    <navigation-button routerLink="/file-upload" label="FileUpload" />
    <navigation-button routerLink="/categorize" label="Categorize" />
    <navigation-button routerLink="/settings" label="Settings" />
    <navigation-button routerLink="/login" label="Login" />`,
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
  imports: [NgOptimizedImage, RouterLink, NavigationButtonComponent],
})
export class HeaderComponent {
  logo = '../../../assets/cashes-logo.png';
}
