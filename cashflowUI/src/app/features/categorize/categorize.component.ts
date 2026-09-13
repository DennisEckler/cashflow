import { Component, inject, OnInit, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CategoryService, TransactionRequest, TransactionService } from '@generated/openapi';
import { Category } from '@src/app/shared/model/category';
import { responseToCategory } from '@src/app/shared/mapper/category.mapper';
import { Transaction } from '@src/app/shared/model/transaction';
import { mapToTransactions } from '@src/app/shared/mapper/transaction.mapper';
import { Identifier } from '@src/app/shared/model/identifier';
import { CashflowButtonComponent } from '@src/app/shared/components/cashflow-button/cashflow-button.component';

interface CategoryOption {
  label: string;
  identifier: Identifier;
}

@Component({
  selector: 'app-categorize',
  templateUrl: './categorize.component.html',
  styleUrls: ['./categorize.component.scss'],
  imports: [FormsModule, CashflowButtonComponent],
})
export class CategorizeComponent implements OnInit {
  transactions = signal<Transaction[]>([]);
  categoryOptions = signal<CategoryOption[]>([]);

  private readonly transactionService = inject(TransactionService);
  private readonly categorySerivce = inject(CategoryService);

  ngOnInit(): void {
    console.log('ngOnInit');
    this.loadState();
  }

  loadState() {
    this.transactionService.getUncategorizedTransactions().subscribe({
      next: (transactionResponse) => {
        this.transactions.set(mapToTransactions(transactionResponse));
      },
      error: (error: HttpErrorResponse) => {
        console.log(`error: ${error.message}`);
      },
    });
    this.categorySerivce.getCategories().subscribe({
      next: (categoryResponse) => {
        this.categoryOptions.set(
          categoryResponse.map((responseCategory) => ({
            label: responseCategory.label!,
            identifier: this.getUndefined(responseToCategory(responseCategory)),
          })),
        );
      },
      error: (error: HttpErrorResponse) => {
        console.log(`error ${error.message}`);
      },
    });
  }

  onSave() {
    const transactionRequests: TransactionRequest[] = this.transactions()
      .filter((transaction) => transaction.identifier !== null)
      .map((transaction) => ({
        amount: transaction.amount!,
        date: transaction.date!,
        id: transaction.id!,
        purpose: transaction.purpose!,
        source: transaction.source!,
        userID: '',
        identifier: transaction.identifier!,
      }));

    console.log(`updating ${transactionRequests.length} transactions`);
    if (transactionRequests.length > 0) {
      this.transactionService.categorizeTransactions(transactionRequests).subscribe({
        next: (v) => {
          console.log(v);
          this.loadState();
        },
        error: (error: HttpErrorResponse) => {
          console.log(`Error message: ${error.message}`);
        },
      });
    }
  }

  recategorize() {
    console.log('recategorize');
    this.transactionService.recategorizeTransactions().subscribe({
      next: (v) => {
        console.log(v);
        this.loadState();
      },
    });
  }

  setIdentifier(transactionID: number, identifier: Identifier | null) {
    console.log('setIdentifier ', transactionID, identifier);
    this.transactions.update((transactions) =>
      transactions.map((transaction) =>
        transaction.id == transactionID ? { ...transaction, identifier } : transaction,
      ),
    );
  }

  getUndefined(category: Category): Identifier {
    const identifier = category.identifier.find(
      (identifier) => identifier.label === category.label + '_default',
    );
    if (identifier === undefined) {
      throw new Error('Cant find undefined identifier for category' + category.label);
    }
    return identifier;
  }
}
