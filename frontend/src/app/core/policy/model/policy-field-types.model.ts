import { Policy } from "./policy.model";

export type policyFieldTypes = {
    [K in keyof Policy]: Policy[K] extends string ? 'string'
        : Policy[K] extends number ? 'number'
        : Policy[K] extends Date ? 'date'
        : 'unknown'
};

export const policyFieldTypes: policyFieldTypes = {
    id: 'string',
    policyStatus: 'string',
    accountNumber: 'string',
    policyNumber: 'string',
    currencyCode: 'string',
    currencySymbol: 'string',
    currencyFractionDigits: 'number',
    policyType: 'string',
    premium: 'number',
    coverageAmount: 'number',
    startDate: 'date',
    endDate: 'date'
};