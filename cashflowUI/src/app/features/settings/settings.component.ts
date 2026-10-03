import { Component, inject, OnInit, signal } from '@angular/core';

import { CategoryCardComponent } from '@src/app/shared/components/category-card/category-card.component';
import {
  CategoryCreateRequest,
  CategoryResponse,
  CategoryService,
  TransactionType,
} from '@generated/openapi';
import { form, FormField } from '@angular/forms/signals';
import { CashflowButtonComponent } from '@src/app/shared/components/cashflow-button/cashflow-button.component';

@Component({
  selector: 'app-settings',
  imports: [CategoryCardComponent, FormField, CashflowButtonComponent],
  template: `
    <div>
      <cashflow-button
        (click)="addCategory()"
        class="header-button"
        label="Add Category"
        data-testid="add-category-button"
      ></cashflow-button>
      <input [formField]="categoryLabelForm" type="text" data-testid="add-category-input-field" />
    </div>
    @if (categories().length !== 0) {
      @for (category of categories(); track category.id) {
        <app-category-card
          (categoryDeleteEvent)="deleteCategory($event)"
          [category]="category"
        ></app-category-card>
      }
    } @else {
      <h2>Can\`t find Categories</h2>
    }
  `,
  styleUrl: './settings.component.scss',
})
export class SettingsComponent implements OnInit {
  categories = signal<CategoryResponse[]>([]);
  categoryLabel = signal('');
  categoryLabelForm = form(this.categoryLabel);
  private categoryService = inject(CategoryService);

  ngOnInit() {
    this.categoryService.getCategories().subscribe({
      next: (v) => {
        if (v !== null) {
          this.categories.set(v);
        }
      },
    });
  }

  addCategory() {
    if (this.categoryLabel() !== '') {
      const labelExist = this.categories().some(
        (category) => category.label === this.categoryLabel(),
      );
      if (labelExist || this.categoryLabel().trim() === '') {
        window.alert('Can`t add duplicates or empty identifier');
      } else {
        let category: CategoryCreateRequest = {
          id: undefined,
          label: this.categoryLabel(),
          userID: undefined,
          type: TransactionType.FIXED,
          identifier: [],
        };
        this.categoryService.addCategory(category).subscribe({
          next: (response) => this.categories.update((category) => [...category, response]),
          error: (err) => console.log(err),
        });
        this.categoryLabel.set('');
      }
    }
  }

  deleteCategory(category: CategoryResponse) {
    this.categories.set(this.categories().filter((ele) => ele.label !== category.label));
    if (category.id !== null) {
      this.categoryService.deleteCatgory(category.id as number).subscribe({
        next: (v) => console.log(v),
      });
    }
  }
}
