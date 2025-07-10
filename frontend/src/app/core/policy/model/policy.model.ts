
export interface Policy {
  id: string;
  typeCode: string;
  typeDisplayName: string;
  statusCode: string;
  statusDisplayName: string;
  policyNumber: string;
  accountNumber: string;
  currencyCode: string;
  currencySymbol: string;
  currencyFractionDigits: number;
  premium: number;
  coverageAmount: number;
  startDate: Date;
  endDate: Date;
}
