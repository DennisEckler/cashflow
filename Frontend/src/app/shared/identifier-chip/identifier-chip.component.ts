import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Identifier } from 'src/app/core/model/identifier';

@Component({
  selector: 'app-identifier-chip',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './identifier-chip.component.html',
  styleUrl: './identifier-chip.component.scss',
})
export class IdentifierChipComponent {
  @Input() identifier?: Identifier;
}
