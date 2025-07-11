export interface Transfer {
  id: string;
  transferNumber: string;
  statusCode: string;
  statusDisplayName: string;
  createdAt: Date;
  amount: number;
  currencyCode: string;
  currencySymbol: string;
  currencyFractionDigits: number;
  description: string;
  sourceAccountNumber: string;
  sourceUserEmail: string;
  targetAccountNumber: string;
  targetUserEmail: string;
}
