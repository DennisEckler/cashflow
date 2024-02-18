import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Category } from 'src/app/core/model/category';
import { Input } from '@angular/core';
import { IdentifierChipComponent } from '../identifier-chip/identifier-chip.component';
import { Identifier } from 'src/app/core/model/identifier';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-category-card',
  standalone: true,
  imports: [CommonModule, IdentifierChipComponent, FormsModule],
  templateUrl: './category-card.component.html',
  styleUrl: './category-card.component.scss',
})
export class CategoryCardComponent {
  @Input() category?: Category;
  identifierInput: string = '';

  deleteIdentifier(deleteIdentifer: Identifier) {
    if (this.category) {
      this.category.identifier = this.category.identifier.filter(
        (identifer) =>
          identifer.identifierLabel !== deleteIdentifer.identifierLabel
      );
    }
  }

  addIdentifier() {
    if (this.identifierInput !== '' && this.category) {
      const labelExist = this.category.identifier.some(
        (identifer) => identifer.identifierLabel === this.identifierInput
      );
      if (labelExist) {
        window.alert('Can`t add duplicates or empty identifier');
      } else {
        this.category.identifier.push({
          identifierID: null,
          identifierLabel: this.identifierInput,
        });
        this.identifierInput = '';
      }
    }
  }

  onChange(event: any) {
    this.identifierInput = event.target.value;
  }
}
