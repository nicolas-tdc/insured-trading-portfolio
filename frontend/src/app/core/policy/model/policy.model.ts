
export interface Policy {
  id: string;
  policyStatus: string;
  accountNumber: string;
  policyNumber: string;
  currencyCode: string;
  currencySymbol: string;
  currencyFractionDigits: number;
  policyType: string;
  premium: number;
  coverageAmount: number;
}
