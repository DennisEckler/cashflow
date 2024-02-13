import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoryCardComponent } from 'src/app/shared/category-card/category-card.component';
import { IdentifierChipComponent } from 'src/app/shared/identifier-chip/identifier-chip.component';
import { NavigationButtonComponent } from 'src/app/shared/navigation-button/navigation-button.component';
import { Category } from 'src/app/core/model/category';
import { CategoryService } from 'src/app/core/services/category.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [
    CommonModule,
    CategoryCardComponent,
    IdentifierChipComponent,
    NavigationButtonComponent,
  ],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss',
})
export class SettingsComponent implements OnInit {
  private categoryService = inject(CategoryService);

  categories?: Category[];

  ngOnInit() {
    this.categoryService.get().subscribe({
      next: (v) => (this.categories = v),
    });
  }

  addCategory() {}

  save() {}
}
