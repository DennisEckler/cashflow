import { TransactionType } from './transactionType';

export interface OverviewContainer {
  period: string;
  overviews: Overview[];
}

export interface Overview {
  year: string;
  month: string;
  type: TransactionType;
  amount: Number;
}

export interface OverviewRow {
  year: string;
  month: string;
  fixed: number;
  variable: number;
  income: number;
  unique: number;
  diff: number;
}
