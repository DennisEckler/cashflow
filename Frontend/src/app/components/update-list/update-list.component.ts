import { Component, OnInit } from '@angular/core';
import { UpdateListService } from './update-list.service';
import { Transaktion } from 'src/app/shared/model/transaktion';
import { HttpErrorResponse } from '@angular/common/http';
import { Category } from '../../shared/enum/category';

@Component({
  selector: 'app-update-list',
  templateUrl: './update-list.component.html',
  styleUrls: ['./update-list.component.scss'],
})
export class UpdateListComponent implements OnInit {
  transaktions?: Transaktion[] = undefined;
  categories: Category[] = Object.values(Category);

  onClick() {
    if (this.transaktions) {
      this.transaktions.forEach((transaktion) => {
        console.log('Category: ' + transaktion.category);
      });
    }
  }

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
}
