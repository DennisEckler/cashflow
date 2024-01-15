import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoryCardComponent } from 'src/app/shared/category-card/category-card.component';
import { IdentifierChipComponent } from 'src/app/shared/identifier-chip/identifier-chip.component';
import { NavigationButtonComponent } from 'src/app/shared/navigation-button/navigation-button.component';

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
export class SettingsComponent {
  addCategory() {}

  save() {}
}
