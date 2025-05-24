export interface Transaction {
  id: string;
  accountId: string;
  amount: number;
  type: string;
  description: string;
  balanceAfter: number;
  createdAt: Date;
}
