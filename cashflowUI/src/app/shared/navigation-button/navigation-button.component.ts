import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
    selector: 'navigation-button',
    templateUrl: './navigation-button.component.html',
    styleUrl: './navigation-button.component.scss',
    imports: [CommonModule, RouterLink]
})
export class NavigationButtonComponent {
  @Input() label = '';
  @Input() routerLink = '';
}
