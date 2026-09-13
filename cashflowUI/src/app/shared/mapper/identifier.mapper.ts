import { IdentifierResponse } from '@generated/openapi';
import { Identifier } from '@src/app/shared/model/identifier';

export function responseToIdentifier(response: IdentifierResponse): Identifier {
  return {
    id: response.id!,
    label: response.label!,
  };
}

export function responseToIdentifiers(response: IdentifierResponse[]): Identifier[] {
  return response.map(responseToIdentifier);
}
