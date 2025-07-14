import { Policy } from "./policy.model";

/**
 * Policy fieldTypes types
 * 
 * @export
 */
export type policyFieldTypes = {
    [K in keyof Policy]: Policy[K] extends string ? 'string'
        : Policy[K] extends number ? 'number'
        : Policy[K] extends Date ? 'date'
        : 'unknown'
};

/**
 * Policy fieldTypes names
 * 
 * @export
 */
export const policyFieldTypes: policyFieldTypes = {
    id: 'string',
    typeCode: 'string',
    typeDisplayName: 'string',
    statusCode: 'string',
    statusDisplayName: 'string',
    accountNumber: 'string',
    policyNumber: 'string',
    currencyCode: 'string',
    currencySymbol: 'string',
    currencyFractionDigits: 'number',
    premium: 'number',
    coverageAmount: 'number',
    startDate: 'date',
    endDate: 'date'
};