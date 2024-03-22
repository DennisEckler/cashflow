import { Identifier } from './identifier';
import { TransactionType } from './transactionType';

export interface Category {
  categoryID: number | null;
  categoryLabel?: string;
  userID: string | null;
  type: TransactionType;
  identifier: Identifier[];
}
