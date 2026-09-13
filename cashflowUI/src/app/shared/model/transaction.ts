import { Identifier } from '@src/app/shared/model/identifier';

export interface Transaction {
  id: number;
  date: string;
  amount: number;
  purpose: string;
  source: string;
  identifier: Identifier | null;
}
