export interface Policy {
  id: string;
  userId: string;
  accountNumber: string;
  policyNumber: string;
  type: string;
  coverageAmount: number;
  premium: number;
  startDate: Date;
  endDate: Date;
  status: string;
}
