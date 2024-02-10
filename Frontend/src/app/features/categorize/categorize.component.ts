import { Component, OnInit, inject } from '@angular/core';
import { Transaktion } from 'src/app/core/model/transaktion';
import { HttpErrorResponse } from '@angular/common/http';
import { Category } from '../../core/enum/category';
import { FormsModule } from '@angular/forms';
import { TransaktionDTO } from 'src/app/core/model/transaktion-dto';
import { NavigationButtonComponent } from 'src/app/shared/navigation-button/navigation-button.component';
import { TransactionService } from 'src/app/core/services/transaction.service';
import { CategoryService } from 'src/app/core/services/category.service';

@Component({
  selector: 'app-categorize',
  templateUrl: './categorize.component.html',
  styleUrls: ['./categorize.component.scss'],
  standalone: true,
  imports: [FormsModule, NavigationButtonComponent],
})
export class CategorizeComponent implements OnInit {
  private transactionService = inject(TransactionService);
  private categorySerivce = inject(CategoryService);
  transaktions?: Transaktion[] = undefined;
  categories: Category[] = Object.values(Category);
  transaktionDTO: TransaktionDTO[] = [];

  constructor() {}

  ngOnInit(): void {
    this.transactionService.getList().subscribe({
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
      if (this.transaktionDTO.length > 0) {
        console.log('sending');
        this.transactionService.saveList(this.transaktionDTO).subscribe({
          next: (v) => console.log(v),
          error: (error: HttpErrorResponse) =>
            console.log(`Error message: ${error.message}`),
        });
        this.transaktionDTO.length = 0;
      }
    }
    this.ngOnInit();
  }
}
