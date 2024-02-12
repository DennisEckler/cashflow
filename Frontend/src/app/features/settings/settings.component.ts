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
        { identifierID: 4, identifierLabel: 'three' },
        { identifierID: 5, identifierLabel: 'three' },
        { identifierID: 6, identifierLabel: 'three' },
        { identifierID: 7, identifierLabel: 'three' },
        { identifierID: 8, identifierLabel: 'three' },
        { identifierID: 9, identifierLabel: 'three' },
        { identifierID: 11, identifierLabel: 'three' },
        { identifierID: 12, identifierLabel: 'three' },
        { identifierID: 13, identifierLabel: 'three' },
        { identifierID: 14, identifierLabel: 'three' },
        { identifierID: 15, identifierLabel: 'three' },
        { identifierID: 16, identifierLabel: 'three' },
        { identifierID: 17, identifierLabel: 'three' },
        { identifierID: 18, identifierLabel: 'three' },
        { identifierID: 19, identifierLabel: 'three' },
        { identifierID: 20, identifierLabel: 'three' },
      ],
    },
  ];

  ngOnInit() {}

  addCategory() {}

  save() {}
}
