/**
 * Account model
 * 
 * @export
 */
export interface Account {
  id: string;
  statusCode: string;
  statusDisplayName: string;
  typeCode: string;
  typeDisplayName: string;
  accountNumber: string;
  balance: number;
  currencyCode: string;
  currencySymbol: string;
  currencyFractionDigits: number;
  policies: string[];
}