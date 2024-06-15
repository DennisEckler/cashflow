import { Identifier } from './identifier';

export interface Transaction {
  id: number;
  date: Date;
  amount: number;
  userID: string;
  purpose: string;
  source: string;
  identifier: Identifier | string;
}
