export interface Transfer {
  id: string;
  createdAt: Date;
  amount: number;
  type: string;
  description: string;
  sourceAccountNumber: string;
  sourceUserEmail: string;
  targetAccountNumber: string;
  targetUserEmail: string;
}
