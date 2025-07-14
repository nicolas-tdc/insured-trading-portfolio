/**
 * Transfer request model
 * 
 * @export
 */
export interface TransferRequest {
  sourceAccountId: string;
  targetAccountNumber: string;
  amount: number;
  description: string;
}