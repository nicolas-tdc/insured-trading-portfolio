export interface TransferRequest {
  sourceAccountId: string;
  targetAccountNumber: string;
  amount: number;
  description: string;
}
