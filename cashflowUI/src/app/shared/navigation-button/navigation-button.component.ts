import { Component, EventEmitter, Input, Output } from '@angular/core';

import { RouterLink } from '@angular/router';

@Component({
    selector: 'navigation-button',
    templateUrl: './navigation-button.component.html',
    styleUrl: './navigation-button.component.scss',
    imports: [RouterLink]
})
export class NavigationButtonComponent {
  @Input() label = '';
  @Input() routerLink = '';
}
