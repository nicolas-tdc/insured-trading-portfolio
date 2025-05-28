export interface Policy {
  id: string;
  accountNumber: string;
  policyNumber: string;
  type: string;
  premium: number;
  coverageAmount: number;
  startDate: Date;
  endDate: Date;
  status: string;
}
