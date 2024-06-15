import { Component, OnInit, inject } from '@angular/core';
import { Transaction } from 'src/app/core/model/transaction';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NavigationButtonComponent } from 'src/app/shared/navigation-button/navigation-button.component';
import { TransactionService } from 'src/app/core/services/transaction.service';
import { CategoryService } from 'src/app/core/services/category.service';
import { Category } from 'src/app/core/model/category';

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
  update: Transaction[] = [];

  constructor() { }

  ngOnInit(): void {
    this.transactionService.getList().subscribe({
      next: (v) => {
        this.transactions = v;
      },
      error: (error: HttpErrorResponse) => {
        console.log(`error: ${error.message}`);
      },
    });
    this.categorySerivce.get().subscribe({
      next: (v) => {
        this.categories = v;
      },
      error: (error: HttpErrorResponse) => {
        console.log(`error ${error.message}`);
      },
    });
  }

  onSave() {
    if (this.transactions) {
      for (let transaction of this.transactions) {
        if (transaction.identifier) {
          transaction.identifier = JSON.parse(transaction.identifier as string);
          this.update.push(transaction);
        }
      }
      if (this.update.length > 0) {
        console.log('sending');
        console.log(this.update);
        this.transactionService.saveList(this.update).subscribe({
          next: (v) => console.log(v),
          error: (error: HttpErrorResponse) =>
            console.log(`Error message: ${error.message}`),
        });
        this.update.length = 0;
      }
    }
    this.ngOnInit();
  }

  getUndefined(category: Category): string {
    return JSON.stringify(
      category.identifier.find((ele) => ele.label === 'undefined')
    );
  }
}
