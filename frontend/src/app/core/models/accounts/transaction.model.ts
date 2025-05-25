export interface Transaction {
  id: string;
  sourceAccountNumber: string;
  sourceUserEmail: string;
  targetAccountNumber: string;
  targetUserEmail: string;
  amount: number;
  type: string;
  description: string;
  balanceAfter: number;
  createdAt: Date;
}
