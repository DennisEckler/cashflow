import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoryCardComponent } from 'src/app/shared/category-card/category-card.component';
import { IdentifierChipComponent } from 'src/app/shared/identifier-chip/identifier-chip.component';
import { NavigationButtonComponent } from 'src/app/shared/navigation-button/navigation-button.component';
import { Category } from 'src/app/core/model/category';

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
  categories?: Category[] = [
    {
      categoryID: 1,
      categoryLabel: 'Dennis',
      userID: 'userID',
      identifier: [{ identifierID: 1, identifierLabel: 'one' }],
    },
    {
      categoryID: 2,
      categoryLabel: 'Sveti',
      userID: 'userID',
      identifier: [
        { identifierID: 2, identifierLabel: 'two' },
        { identifierID: 3, identifierLabel: 'three' },
      ],
    },
  ];

  ngOnInit() {}

  addCategory() {}

  save() {}
}
