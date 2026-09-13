import { TransactionType } from '@generated/openapi';
import { Identifier } from '@src/app/shared/model/identifier';

export interface Category {
  id: number;
  label: string;
  type: TransactionType;
  identifier: Identifier[];
}
