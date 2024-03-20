import { Component, EventEmitter, Output, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Category } from 'src/app/core/model/category';
import { Input } from '@angular/core';
import { IdentifierChipComponent } from '../identifier-chip/identifier-chip.component';
import { Identifier } from 'src/app/core/model/identifier';
import { FormsModule } from '@angular/forms';
import { IdentifierService } from 'src/app/core/services/identifier.service';
import { IdentifierDTO } from 'src/app/core/model/identifierDto';

@Component({
  selector: 'app-category-card',
  standalone: true,
  imports: [CommonModule, IdentifierChipComponent, FormsModule],
  templateUrl: './category-card.component.html',
  styleUrl: './category-card.component.scss',
})
export class CategoryCardComponent {
  private identifierSerice = inject(IdentifierService);
  @Input() category?: Category;
  @Output() categoryDelete = new EventEmitter<Category>();

  identifierInput: string = '';

  deleteCategory() {
    this.categoryDelete.emit(this.category);
  }

  deleteIdentifier(deleteIdentifer: Identifier) {
    if (this.category) {
      this.category.identifier = this.category.identifier.filter(
        (identifer) =>
          identifer.identifierLabel !== deleteIdentifer.identifierLabel
      );
      if (deleteIdentifer.identifierID !== null) {
        this.identifierSerice.delete(deleteIdentifer).subscribe({
          next: (v) => console.log(v),
        });
      }
    }
  }

  addIdentifier() {
    if (this.identifierInput !== '' && this.category) {
      const labelExist = this.category.identifier.some(
        (identifer) => identifer.identifierLabel === this.identifierInput
      );
      if (labelExist || this.identifierInput.trim() === '') {
        window.alert('Can`t add duplicates or empty identifier');
      } else {
        const identifierDto: IdentifierDTO = {
          identifierLabel: this.identifierInput,
          categoryID: this.category.categoryID,
        };
        this.identifierSerice.save(identifierDto).subscribe({
          next: (response) => this.category?.identifier.push(response),
        });
        this.identifierInput = '';
      }
    }
  }
}
