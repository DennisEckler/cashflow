import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Identifier } from 'src/app/core/model/identifier';

@Component({
    selector: 'app-identifier-chip',
    imports: [CommonModule],
    templateUrl: './identifier-chip.component.html',
    styleUrl: './identifier-chip.component.scss'
})
export class IdentifierChipComponent {
  @Input() identifier?: Identifier;
  @Output() identifierDeleted = new EventEmitter<Identifier>();
  newIdentifier?: Identifier;

  deleteIdentifier() {
    this.identifierDeleted.emit(this.identifier);
  }
}
