import { Account } from './account.model';

/**
 * Account fieldTypes types
 * 
 * @export
 */
export type AccountFieldTypes = {
  [K in keyof Account]: Account[K] extends string ? 'string'
                      : Account[K] extends number ? 'number'
                      : Account[K] extends string[] ? 'string[]'
                      : 'unknown';
};

/**
 * Account fieldTypes names
 * 
 * @export
 */
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
