import { Component, Input } from '@angular/core';

import { RouterLink } from '@angular/router';

@Component({
  selector: 'cashflow-button',
  templateUrl: './cashflow-button.component.html',
  styleUrl: './cashflow-button.component.scss',
  imports: [RouterLink],
})
export class CashflowButtonComponent {
  @Input() label = '';
  @Input() routerLink = '';
}
