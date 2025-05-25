export interface Policy {
  id: string;
  userId: string;
  policyNumber: string;
  type: string;
  coverageAmount: number;
  premium: number;
  startDate: Date;
  endDate: Date;
  status: string;
  accountId: string;
}
