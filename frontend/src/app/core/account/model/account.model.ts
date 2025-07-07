export interface Account {
  id: string;
  accountStatus: string;
  accountType: string;
  accountNumber: string;
  balance: number;
  currencyCode: string;
  currencySymbol: string;
  currencyFractionDigits: number;
  policies: string[];
}