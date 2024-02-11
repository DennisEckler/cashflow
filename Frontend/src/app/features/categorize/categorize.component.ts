import { Component, OnInit, inject } from '@angular/core';
import { Transaction } from 'src/app/core/model/transaction';
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
  transactions?: Transaction[];
  categories?: Category[];
  transaktionDTO: TransaktionDTO[] = [];

  constructor() {}

  ngOnInit(): void {
    this.transactionService.getList().subscribe({
      next: (v) => {
        this.transactions = v;
        console.log(
          'get request was succesfull and transactions are filled with length of: ' +
            this.transactions?.length,
        );
        console.log(this.transactions);
      },
      error: (error: HttpErrorResponse) => {
        console.log(`error: ${error.message}`);
      },
      complete: () => console.log('complete'),
    });
    this.categorySerivce.get().subscribe({
      next: (v) => {
        this.categories = v;
        console.log(this.categories);
      },
      error: (error: HttpErrorResponse) => {
        console.log(`error ${error.message}`);
      },
    });
  }

  onClick() {
    if (this.transactions) {
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
