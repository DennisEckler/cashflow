import { Component, inject, OnInit, signal } from '@angular/core';

import { CategoryCardComponent } from '@src/app/shared/components/category-card/category-card.component';
import { NavigationButtonComponent } from '@src/app/shared/components/navigation-button/navigation-button.component';
import {
  CategoryCreateRequest,
  CategoryResponse,
  CategoryService,
  TransactionType,
} from '@generated/openapi';
import { form, FormField } from '@angular/forms/signals';

@Component({
  selector: 'app-settings',
  imports: [CategoryCardComponent, NavigationButtonComponent, FormField],
  templateUrl: './settings.component.html',
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
          next: (response) =>
            this.categories.update((category) => [...category, response]),
          error: (err) => console.log(err),
        });
        this.categoryLabel.set('');
      }
    }
  }

  deleteCategory(category: CategoryResponse) {
    this.categories.set(
      this.categories().filter((ele) => ele.label !== category.label),
    );
    if (category.id !== null) {
      this.categoryService.deleteCatgory(category.id as number).subscribe({
        next: (v) => console.log(v),
      });
    }
  }
}
