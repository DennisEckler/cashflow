import { Identifier } from './identifier';
import { TransactionType } from './transactionType';

export interface Category {
  id: number | null;
  label?: string;
  userID: string | null;
  type: TransactionType;
  identifier: Identifier[];
}
