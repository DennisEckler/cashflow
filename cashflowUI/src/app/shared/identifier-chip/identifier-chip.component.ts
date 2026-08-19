import { Component, EventEmitter, Input, Output, ChangeDetectionStrategy } from '@angular/core';

import {
  IdentifierCreateRequest,
  IdentifierResponse,
} from 'generated-sources/openapi';

@Component({
  selector: 'app-identifier-chip',
  imports: [],
  templateUrl: './identifier-chip.component.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './identifier-chip.component.scss',
})
export class IdentifierChipComponent {
  @Input() identifier?: IdentifierResponse;
  @Output() identifierDeleted = new EventEmitter<IdentifierResponse>();
  newIdentifier?: IdentifierCreateRequest;

  deleteIdentifier() {
    this.identifierDeleted.emit(this.identifier);
  }
}
