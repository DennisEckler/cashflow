import { Component, inject, OnInit, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NavigationButtonComponent } from '@src/app/shared/components/navigation-button/navigation-button.component';
import {
  CategoryResponse,
  CategoryService,
  TransactionRequest,
  TransactionResponse,
  TransactionService,
} from '@generated/openapi';
import { Category } from '@src/app/shared/model/category';
import { responseToCategories } from '@src/app/shared/mapper/category.mapper';

@Component({
  selector: 'app-categorize',
  templateUrl: './categorize.component.html',
  styleUrls: ['./categorize.component.scss'],
  imports: [FormsModule, NavigationButtonComponent],
})
export class CategorizeComponent implements OnInit {
  transactions?: TransactionResponse[];
  categories = signal<Category[]>([]);
  update: TransactionRequest[] = [];
  private readonly transactionService = inject(TransactionService);
  private readonly categorySerivce = inject(CategoryService);

  ngOnInit(): void {
    console.log('ngOnInit');
    this.transactionService.getUncategorizedTransactions().subscribe({
      next: (v) => {
        this.transactions = v;
      },
      error: (error: HttpErrorResponse) => {
        console.log(`error: ${error.message}`);
      },
    });
    this.categorySerivce.getCategories().subscribe({
      next: (v) => {
        this.categories.set(responseToCategories(v));
      },
      error: (error: HttpErrorResponse) => {
        console.log(`error ${error.message}`);
      },
    });
  }

  onSave() {
    this.update.length = 0;
    if (this.transactions) {
      for (let transaction of this.transactions!) {
        if (transaction.identifier?.id !== null) {
          var identifierRequest = JSON.parse(transaction.identifier as string);
          if (identifierRequest.id !== null) {
            var transactionRequest: TransactionRequest = {
              amount: transaction.amount!,
              date: transaction.date!,
              id: transaction.id!,
              purpose: transaction.purpose!,
              source: transaction.source!,
              userID: transaction.userID!,
              identifier: identifierRequest,
            };
            this.update.push(transactionRequest);
          }
        }
      }
      console.log(`updating ${this.update.length} transactions`);
      if (this.update.length > 0) {
        console.log('sending');
        console.log(this.update);
        this.transactionService.categorizeTransactions(this.update).subscribe({
          next: (v) => {
            console.log(v);
            this.ngOnInit();
          },
          error: (error: HttpErrorResponse) => {
            console.log(`Error message: ${error.message}`);
          },
          complete: () => (this.update.length = 0),
        });
      }
    }
  }

  recategorize() {
    console.log('recategorize');
    this.transactionService.recategorizeTransactions().subscribe({
      next: (v) => {
        console.log(v);
        this.ngOnInit();
      },
    });
  }

  getUndefined(category: CategoryResponse): string {
    var identifier = category.identifier!.find((ele) => ele.label === category.label + '_default');
    if (identifier === undefined) {
      throw new Error('Cant find undefined identifier for category' + category.label);
    }
    return JSON.stringify(identifier);
  }
}
