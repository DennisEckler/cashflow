import { Component, OnInit } from '@angular/core';
import { UpdateListService } from './update-list.service';
import { Transaktion } from 'src/app/shared/model/transaktion';
import { HttpErrorResponse } from '@angular/common/http';
import { Category } from '../../shared/enum/category';
import { FormsModule } from '@angular/forms';

export interface CategoryIdentifier {
  id: number;
  category: String;
}

@Component({
    selector: 'app-update-list',
    templateUrl: './update-list.component.html',
    styleUrls: ['./update-list.component.scss'],
    standalone: true,
    imports: [FormsModule],
})
export class UpdateListComponent implements OnInit {
  transaktions?: Transaktion[] = undefined;
  categories: Category[] = Object.values(Category);
  minimizedTransaktion: CategoryIdentifier[] = [];

  constructor(private updateListService: UpdateListService) {}

  ngOnInit(): void {
    this.updateListService.getList().subscribe({
      next: (v) => {
        this.transaktions = v;
        console.log(
          'get request was succesfull and transaktions are filled with length of: ' +
            this.transaktions?.length,
        );
        console.log(this.transaktions);
      },
      error: (error: HttpErrorResponse) => {
        console.log(`error: ${error.message}`);
      },
      complete: () => console.log('complete'),
    });
  }

  onClick() {
    if (this.transaktions) {
      this.transaktions.forEach((transaktion) => {
        if (transaktion.category.toString() !== 'LEER') {
          this.minimizedTransaktion.push({
            id: transaktion.id,
            category: transaktion.category.toUpperCase(),
          });
        }
      });

      if (this.minimizedTransaktion.length > 0) {
        console.log('sending');
        this.updateListService.saveList(this.minimizedTransaktion).subscribe({
          next: (v) => console.log(v),
          error: (error: HttpErrorResponse) =>
            console.log(`Error message: ${error.message}`),
        });
        this.minimizedTransaktion.length = 0;
      }
    }
    this.ngOnInit();
  }
}
