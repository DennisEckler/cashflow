import { Component, input, output } from '@angular/core';

import { IdentifierResponse } from '@generated/openapi';

@Component({
  selector: 'app-identifier-chip',
  template: `
    <div class="identifier-container">
      <p class="identifier-chip" [attr.data-testid]="'identifier-chip-label-' + identifier().label">
        {{ identifier().label }}
      </p>
      <button
        (click)="deleteIdentifier()"
        class="identifier-button"
        [attr.data-testid]="'identifier-chip-button-' + identifier().label"
      >
        <svg class="identifier-button-icon" width="20" height="20" viewBox="0 0 20 20">
          <circle cx="10" cy="10" r="9" fill="none" stroke="white" stroke-width="2" />
          <line x1="6" y1="6" x2="14" y2="14" stroke="white" stroke-width="2" />
          <line x1="6" y1="14" x2="14" y2="6" stroke="white" stroke-width="2" />
        </svg>
      </button>
    </div>
  `,
  styleUrl: './identifier-chip.component.scss',
})
export class IdentifierChipComponent {
  identifier = input.required<IdentifierResponse>();
  identifierDeleted = output<IdentifierResponse>();

  deleteIdentifier() {
    this.identifierDeleted.emit(this.identifier());
  }
}
