export interface Transfer {
  id: string;
  transferNumber: string;
  createdAt: Date;
  amount: number;
  description: string;
  sourceAccountNumber: string;
  sourceUserEmail: string;
  targetAccountNumber: string;
  targetUserEmail: string;
}
