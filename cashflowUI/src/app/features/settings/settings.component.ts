import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoryCardComponent } from 'src/app/shared/category-card/category-card.component';
import { IdentifierChipComponent } from 'src/app/shared/identifier-chip/identifier-chip.component';
import { NavigationButtonComponent } from 'src/app/shared/navigation-button/navigation-button.component';
import { Category } from 'src/app/core/model/category';
import { CategoryService } from 'src/app/core/services/category.service';
import { FormsModule } from '@angular/forms';
import { TransactionType } from 'src/app/core/model/transactionType';

@Component({
    selector: 'app-settings',
    imports: [
        CommonModule,
        CategoryCardComponent,
        IdentifierChipComponent,
        NavigationButtonComponent,
        FormsModule,
    ],
    templateUrl: './settings.component.html',
    styleUrl: './settings.component.scss'
})
export class SettingsComponent implements OnInit {
  private categoryService = inject(CategoryService);

  categories: Category[] = [];
  categoryInput: string = '';

  ngOnInit() {
    this.categoryService.get().subscribe({
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
        let category: Category = {
          id: null,
          label: this.categoryInput,
          userID: null,
          type: TransactionType.FIXED,
          identifier: [],
        };
        this.categoryService.add(category).subscribe({
          next: (response) => this.categories.push(response),
          error: (err) => console.log(err),
        });
        this.categoryInput = '';
      }
    }
  }

  deleteCategory(category: Category) {
    this.categories = this.categories?.filter(
      (ele) => ele.label !== category.label,
    );
    if (category.id !== null) {
      this.categoryService.delete(category).subscribe({
        next: (v) => console.log(v),
      });
    }
  }
}
