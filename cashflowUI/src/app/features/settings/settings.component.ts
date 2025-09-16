import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoryCardComponent } from 'src/app/shared/category-card/category-card.component';
import { NavigationButtonComponent } from 'src/app/shared/navigation-button/navigation-button.component';
import { FormsModule } from '@angular/forms';
import {
  CategoryCreateRequest,
  CategoryResponse,
  CategoryService,
  TransactionType,
} from 'generated-sources/openapi';

@Component({
  selector: 'app-settings',
  imports: [
    CommonModule,
    CategoryCardComponent,
    NavigationButtonComponent,
    FormsModule,
  ],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss',
})
export class SettingsComponent implements OnInit {
  private categoryService = inject(CategoryService);

  categories: CategoryResponse[] = [];
  categoryInput: string = '';

  ngOnInit() {
    this.categoryService.getCategories().subscribe({
      next: (v) => {
        if (v !== null) {
          this.categories = v;
        }
      },
    });
  }

  addCategory() {
    if (this.categoryInput !== '') {
      const labelExist = this.categories.some(
        (category) => category.label === this.categoryInput,
      );
      if (labelExist || this.categoryInput.trim() === '') {
        window.alert('Can`t add duplicates or empty identifier');
      } else {
        let category: CategoryCreateRequest = {
          id: undefined,
          label: this.categoryInput,
          userID: undefined,
          type: TransactionType.FIXED,
          identifier: [],
        };
        this.categoryService.addCategory(category).subscribe({
          next: (response) => this.categories.push(response),
          error: (err) => console.log(err),
        });
        this.categoryInput = '';
      }
    }
  }

  deleteCategory(category: CategoryResponse) {
    this.categories = this.categories?.filter(
      (ele) => ele.label !== category.label,
    );
    if (category.id !== null) {
      this.categoryService.deleteCatgory(category.id as number).subscribe({
        next: (v) => console.log(v),
      });
    }
  }
}
