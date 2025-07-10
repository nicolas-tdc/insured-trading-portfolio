import { Account } from './account.model';

export type AccountFieldTypes = {
  [K in keyof Account]: Account[K] extends string ? 'string'
                      : Account[K] extends number ? 'number'
                      : Account[K] extends string[] ? 'string[]'
                      : 'unknown';
};

export const accountFieldTypes: AccountFieldTypes = {
  id: 'string',
  statusCode: 'string',
  statusDisplayName: 'string',
  typeCode: 'string',
  typeDisplayName: 'string',
  accountNumber: 'string',
  balance: 'number',
  currencyCode: 'string',
  currencySymbol: 'string',
  currencyFractionDigits: 'number',
  policies: 'string[]'
};
