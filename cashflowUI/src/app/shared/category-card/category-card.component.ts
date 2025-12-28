import { Component, EventEmitter, Output, inject } from '@angular/core';

import { Input } from '@angular/core';
import { IdentifierChipComponent } from '../identifier-chip/identifier-chip.component';
import { FormsModule } from '@angular/forms';
import {
  CategoryResponse,
  CategoryService,
  IdentifierCreateRequest,
  IdentifierResponse,
  IdentifierService,
  TransactionType,
} from 'generated-sources/openapi';
import { catResToCatUpReq } from 'src/app/core/mapper/category.mapper';

@Component({
  selector: 'app-category-card',
  imports: [IdentifierChipComponent, FormsModule],
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

  @Input() category!: CategoryResponse;
  @Output() categoryDeleteEvent = new EventEmitter<CategoryResponse>();

  identifierInput: string = '';
  transactionType = TransactionType;
  enumValues: string[];

  changeCategoryType() {
    var catUpdateReq = catResToCatUpReq(this.category);

    this.categoryService
      .updateCategory(catUpdateReq.id, catUpdateReq)
      .subscribe({
        next: (response) => (this.category = response),
        error: (err) => console.log('value not accepted'),
      });
  }

  deleteCategory() {
    this.categoryDeleteEvent.emit(this.category);
  }

  deleteIdentifier(deleteIdentifer: IdentifierResponse) {
    if (this.category && this.category.identifier) {
      this.category.identifier = this.category.identifier.filter(
        (identifer) => identifer.label !== deleteIdentifer.label,
      );
      if (deleteIdentifer.id !== null) {
        this.identifierSerice
          .deleteIdentifier(this.category.id!, deleteIdentifer.id!)
          .subscribe({
            next: (v) => console.log(v),
          });
      }
    }
  }

  addIdentifier() {
    if (this.identifierInput !== '' && this.category) {
      const labelExist = this.category.identifier!.some(
        (identifer) => identifer.label === this.identifierInput,
      );
      if (labelExist || this.identifierInput.trim() === '') {
        window.alert('Can`t add duplicates or empty identifier');
      } else {
        const identifier: IdentifierCreateRequest = {
          label: this.identifierInput,
          categoryID: this.category.id!,
        };
        this.identifierSerice
          .addIdentifier(identifier.categoryID, identifier)
          .subscribe({
            next: (response) => this.category?.identifier?.push(response),
          });
        this.identifierInput = '';
      }
    }
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
}
