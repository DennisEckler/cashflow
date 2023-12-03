import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'navigation-button',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `<button class="btn" routerLink="{{ routerLink }}">
    {{ label }}
  </button>`,
  styleUrl: './navigation-button.component.scss',
})
export class NavigationButtonComponent {
  @Input() label = '';
  @Input() routerLink = '';
}
