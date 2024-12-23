import { Component } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { HeaderComponent } from './core/layout/header/header.component';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
    imports: [HeaderComponent, RouterOutlet, RouterLink]
})
export class AppComponent {
  title = 'Cashflow';
}
