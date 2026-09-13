import { TransactionResponse } from '@generated/openapi';
import { Transaction } from '@src/app/shared/model/transaction';
import { responseToIdentifier } from '@src/app/shared/mapper/identifier.mapper';

export function mapToTransaction(response: TransactionResponse): Transaction {
  const identifier =
    response.identifier?.id !== null ? responseToIdentifier(response.identifier!) : null;
  return {
    id: response.id!,
    date: response.date!,
    source: response.source!,
    purpose: response.purpose!,
    amount: response.amount!,
    identifier,
  };
}

export function mapToTransactions(response: TransactionResponse[]): Transaction[] {
  return response.map(mapToTransaction);
}
