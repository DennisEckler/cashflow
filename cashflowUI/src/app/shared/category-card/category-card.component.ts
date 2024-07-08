import {
  Component,
  EventEmitter,
  Output,
  ViewChild,
  inject,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { Category } from 'src/app/core/model/category';
import { Input } from '@angular/core';
import { IdentifierChipComponent } from '../identifier-chip/identifier-chip.component';
import { Identifier } from 'src/app/core/model/identifier';
import { FormsModule } from '@angular/forms';
import { IdentifierService } from 'src/app/core/services/identifier.service';
import { IdentifierDTO } from 'src/app/core/model/identifierDto';
import { TransactionType } from 'src/app/core/model/transactionType';
import { CategoryService } from 'src/app/core/services/category.service';

@Component({
  selector: 'app-category-card',
  standalone: true,
  imports: [CommonModule, IdentifierChipComponent, FormsModule],
  templateUrl: './category-card.component.html',
  styleUrl: './category-card.component.scss',
})
export class CategoryCardComponent {
  constructor() {
    this.enumValues = Object.keys(this.transactionType);
  }

  private identifierSerice = inject(IdentifierService);
  private categoryService = inject(CategoryService);
  dialog!: HTMLDialogElement;

  @Input() category!: Category;
  @Output() categoryDelete = new EventEmitter<Category>();

  identifierInput: string = '';
  transactionType = TransactionType;
  enumValues: string[];

  changeType() {
    this.categoryService.change(this.category).subscribe({
      next: (response) => (this.category = response),
      error: (err) => console.log('value not accepted'),
    });
  }

  deleteCategory() {
    this.categoryDelete.emit(this.category);
  }

  clickedEvent() {
    this.dialog = document.getElementById('dialog') as HTMLDialogElement;
    this.dialog.showModal();
  }
  closeDialog() {
    if (this.dialog) {
      this.dialog.close();
    }
  }

  deleteIdentifier(deleteIdentifer: Identifier) {
    if (this.category) {
      this.category.identifier = this.category.identifier.filter(
        (identifer) => identifer.label !== deleteIdentifer.label
      );
      if (deleteIdentifer.id !== null) {
        this.identifierSerice.delete(deleteIdentifer).subscribe({
          next: (v) => console.log(v),
        });
      }
    }
  }

  addIdentifier() {
    if (this.identifierInput !== '' && this.category) {
      const labelExist = this.category.identifier.some(
        (identifer) => identifer.label === this.identifierInput
      );
      if (labelExist || this.identifierInput.trim() === '') {
        window.alert('Can`t add duplicates or empty identifier');
      } else {
        const identifierDto: IdentifierDTO = {
          label: this.identifierInput,
          categoryID: this.category.id,
        };
        this.identifierSerice.save(identifierDto).subscribe({
          next: (response) => this.category?.identifier.push(response),
        });
        this.identifierInput = '';
      }
    }
  }
}
