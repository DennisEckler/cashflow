import { Identifier } from './identifier';

export interface Transaktion {
  transactionID: number;
  date: Date;
  amount: number;
  userID: string;
  purpose: string;
  source: string;
  identifier: Identifier;
}
